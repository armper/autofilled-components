package org.vaadin.addons.pandalyte.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;
import org.mockito.Mockito;
import org.vaadin.addons.pandalyte.AutoFormFiller;
import org.vaadin.addons.pandalyte.provider.AiServiceProvider;
import org.vaadin.addons.pandalyte.supportProfile.OwnerForm;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class AutoFormFillerTest {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AutoFormFillerTest.class);

    private AutoFormFiller autoFormFiller;
    private AiServiceProvider mockGptServiceProvider = Mockito.mock(AiServiceProvider.class);

    @Test
    public void testAutoFill() {
        // Given
        String userPrompt = "I want to create a public Support Profile called 'Super Support' with the owner named "
                +
                "'Jane Doe', a person, living at '1234 Main Street, Springfield', and reachable at mobile phone '505-555-1234'. "
                +
                "For impacts, I'd like to include two. The first impact is named 'System Downtime', " +
                "has a severity of 'High', and the threshold  is 'over 30 minutes'. The second " +
                "impact is 'Data Loss', with a severity of 'Medium' and the threshold phrase is 'more " +
                "than 1 GB'. I have 3 consumers. The first consumer is 'Alice' living at '5678 Elm " +
                "Street, Springfield' with phone number '555-6789' and type 'Individual'. The second " +
                "consumer is 'Bob' living at '9101 Maple Street, Springfield' with phone number " +
                "'555-9101' and type 'Business'. The third consumer is 'Carol' living at '11213 Oak " +
                "Street, Springfield' with phone number '555-1121' and type 'Government'.";

        String gptInstructions = "Fill out N/A in the JSON value if the user did not specify a value. If the owner is a person, set the owner type to INDIVIDUAL in all caps.";

        VerticalLayout supportProfileComponent = new VerticalLayout();
        supportProfileComponent.setId("Support Profile");
        TextField nameField = new TextField("Name");
        nameField.setId("name");
        supportProfileComponent.add(nameField);
        OwnerForm ownerForm = new OwnerForm();
        ownerForm.setId("ownerForm");
        supportProfileComponent.add(ownerForm);

        autoFormFiller = new AutoFormFiller(supportProfileComponent, "abc123");
        autoFormFiller.setGptProvider(mockGptServiceProvider);

        // Prepare the mock response from GPT service provider
        Map<String, Object> gptResponse = new HashMap<>();
        gptResponse.put("name", "Super Support");
        gptResponse.put("ownerName", "Jane");
        gptResponse.put("address", "1234 Main Street, Springfield");
        gptResponse.put("number", "505-555-1234");
        gptResponse.put("phoneType", "Mobile");
        gptResponse.put("type", "INDIVIDUAL");
        Mockito.when(mockGptServiceProvider.processRequest(Mockito.anyString())).thenReturn(gptResponse);

        // When
        autoFormFiller.autoFill(userPrompt, gptInstructions);

        // Then
        // Verify that the GPT service provider is called with the correct arguments
        Mockito.verify(mockGptServiceProvider, Mockito.times(1)).processRequest(Mockito.anyString());

        Map<String, String> componentValues = collectAllComponentValues(autoFormFiller);

        log.info("Component values: {}", componentValues);

        assertEquals("Super Support", componentValues.get("name"));
        assertEquals("Jane", componentValues.get("ownerName"));
        assertEquals("1234 Main Street, Springfield", componentValues.get("address"));
        assertEquals("505-555-1234", componentValues.get("number"));
        assertEquals("Mobile", componentValues.get("phoneType"));
        assertEquals("INDIVIDUAL", componentValues.get("type"));
    }

    private Map<String, String> collectAllComponentValues(Component component) {
        Map<String, String> componentValues = new HashMap<>();
        getAllChildren(component).forEach(child -> {
            String id = child.getId().orElse("Unnamed");
            String value = "N/A";
            if (child instanceof TextField) {
                value = ((TextField) child).getValue();
            } else if (child instanceof ComboBox) {
                ComboBox<?> comboBox = (ComboBox<?>) child;
                value = (comboBox.getValue() != null) ? comboBox.getValue().toString() : "null";
            } else if (child instanceof Checkbox) {
                value = String.valueOf(((Checkbox) child).getValue());
            } else if (child instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) child;
                value = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "null";
            } else if (child instanceof Grid) {
                Grid<?> grid = (Grid<?>) child;
                value = "Grid with " + grid.getDataCommunicator().getItemCount() + " items";
            }
            componentValues.put(id, value);
        });
        return componentValues;
    }

    private Stream<Component> getAllChildren(Component component) {
        return Stream.concat(
                Stream.of(component),
                component.getChildren().flatMap(this::getAllChildren));
    }

}
