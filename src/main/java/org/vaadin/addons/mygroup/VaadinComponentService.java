package org.vaadin.addons.mygroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.textfield.TextField;

public class VaadinComponentService {

    public Map<String, Object> createJsonFromComponent(Component component) {
        List<VaadinComponentInfo> componentInfoList = new ArrayList<>();
        findChildComponents(component, componentInfoList);

        return buildJsonFromComponentInfoList(componentInfoList);
    }

    private void findChildComponents(Component component, List<VaadinComponentInfo> componentInfoList) {
        component.getChildren().forEach(childComponent -> {
            String componentType = childComponent.getClass().getSimpleName();
            String id = childComponent.getId().orElse(null);

            if (id != null) {
                componentInfoList.add(new VaadinComponentInfo(id, componentType, childComponent));
            }

            findChildComponents(childComponent, componentInfoList);
        });
    }

    private Map<String, Object> buildJsonFromComponentInfoList(List<VaadinComponentInfo> componentInfoList) {
        Map<String, Object> json = new HashMap<>();
        for (VaadinComponentInfo componentInfo : componentInfoList) {
            String id = componentInfo.getId();
            if (id != null && !id.isEmpty()) {
                if (isContainedInCustomForm(componentInfo.getComponent())) {
                    json.put(id, buildJsonFromComponentInfoList(componentInfo.getChildren()));
                } else if (componentInfo.getComponent() instanceof Grid
                        || componentInfo.getComponent() instanceof MultiSelectListBox) {
                    json.put(id, new ArrayList<>());
                } else {
                    json.put(id, "");
                }
            }
        }
        return json;
    }

    private boolean isContainedInCustomForm(Component component) {
        if (component == null) {
            return false;
        }

        List<Component> children = component.getChildren().collect(Collectors.toList());
        if (children.isEmpty()) {
            return false;
        }

        boolean hasFormField = false;
        for (Component child : children) {
            if (child instanceof TextField || child instanceof ComboBox || child instanceof Grid) {
                hasFormField = true;
                break;
            }
        }

        return hasFormField;
    }
}
