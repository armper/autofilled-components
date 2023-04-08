package org.vaadin.addons.mygroup;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;

public class AutoFormFiller extends Div {
    private static final Logger logger = LoggerFactory.getLogger(AutoFormFiller.class);

    private final VaadinComponentService vaadinComponentService;
    private final GptService gptService;

    private String userPrompt;
    private String gptInstructions;

    public AutoFormFiller(String userPrompt, String gptInstructions, String apiKey) {
        this.vaadinComponentService = new VaadinComponentService();
        this.gptService = new GptService(apiKey);

        this.userPrompt = userPrompt;
        this.gptInstructions = gptInstructions;
    }

    public AutoFormFiller(Component component, String userPrompt, String gptInstructions, String apiKey) {
        this(userPrompt, gptInstructions, apiKey);
        add(component);

        var json = processComponents(component);
        fillInComponentsBasedOnJson(json, userPrompt, gptInstructions);
    }

    public AutoFormFiller(Component[] components, String userPrompt, String gptInstructions, String apiKey) {
        this(userPrompt, gptInstructions, apiKey);
        add(components);

        var json = processComponents(components);
        fillInComponentsBasedOnJson(json, userPrompt, gptInstructions);
    }

    public void setComponents(Component... components) {
        removeAll();
        add(components);

        var json = processComponents(components);
        fillInComponentsBasedOnJson(json, userPrompt, gptInstructions);
    }

    private void fillInComponentsBasedOnJson(Map<String, Object> json, String userPrompt, String gptInstructions) {
        String gptRequest = String.format(
                "Based on the user input: '%s', generate a JSON object according to these instructions: '%s'. "
                        + "Return the result as a JSON object in this format: '%s'. Infer the missing values based upon the user input and instructions.",
                userPrompt, gptInstructions, json);

        Map<String, Object> gptResponse = gptService.callGptApi(gptRequest);

        fillComponentsWithGptResponse(gptResponse);
    }

    public Map<String, Object> processComponents(Component... components) {
        return processComponents(new Div(components));
    }

    public Map<String, Object> processComponents(Component component) {
        Map<String, Object> json = vaadinComponentService.createJsonFromComponent(component);

        logger.debug("Generated JSON: {}", json);

        return json;
    }

    private void fillComponentsWithGptResponse(Map<String, Object> gptResponse) {
        logger.info("Filling components with GPT response: {}", gptResponse);

        List<Component> components = getAllChildren(this).collect(Collectors.toList());
        logger.info("Found {} components",
                components.stream().map(c -> c.getId().orElse(null)).collect(Collectors.toList()));

        for (Component component : components) {
            fillComponentAndChildrenWithGptResponse(component, gptResponse, "");
        }
    }

    private Stream<Component> getAllChildren(Component component) {
        return Stream.concat(
                Stream.of(component),
                component.getChildren().flatMap(this::getAllChildren));
    }

    private void fillComponentAndChildrenWithGptResponse(Component component, Map<String, Object> gptResponse,
            String parentIds) {
        String id = component.getId().orElse(null);
        String fullId = parentIds.isEmpty() ? id : parentIds + "." + id;

        if (component instanceof Grid) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) gptResponse.get(fullId);
            Grid<?> grid = (Grid<?>) component;
            Class<?> beanType = grid.getBeanType();
            updateGridWithWildcards(grid, items, beanType);
        }

        // Process children of the component
        List<Component> children = component.getChildren().collect(Collectors.toList());
        for (Component child : children) {
            fillComponentAndChildrenWithGptResponse(child, gptResponse, parentIds.isEmpty() ? "" : parentIds + ".");
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void updateGridWithWildcards(Grid<?> grid, List<Map<String, Object>> items, Class<?> beanType) {
        updateGrid((Grid<T>) grid, items, (Class<T>) beanType);
    }

    private <T> void updateGrid(Grid<T> grid, List<Map<String, Object>> items, Class<T> itemClass) {
        List<T> gridItems = items.stream().map(itemMap -> {
            T item;
            try {
                item = itemClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new IllegalStateException("Failed to create a new instance of the item class", e);
            }

            for (Map.Entry<String, Object> entry : itemMap.entrySet()) {
                String propName = entry.getKey();
                Object propValue = entry.getValue();
                Grid.Column<T> column = grid.getColumnByKey(propName);
                if (column != null && column.getEditorComponent() != null) {
                    column.getEditorComponent().getElement().setProperty("value", propValue.toString());
                }

                try {
                    Field field = itemClass.getDeclaredField(propName);
                    field.setAccessible(true);
                    field.set(item, propValue);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    logger.error("Failed to set field value for '{}': {}", propName, e.getMessage());
                }
            }
            return item;
        }).collect(Collectors.toList());

        grid.setItems(gridItems);
    }

}
