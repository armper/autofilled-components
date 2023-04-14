package org.vaadin.addons.pandalyte.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.vaadin.addons.pandalyte.service.VaadinComponentService;
import org.vaadin.addons.pandalyte.supportProfile.OwnerForm;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class VaadinComponentServiceTest {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(VaadinComponentServiceTest.class);

    private VaadinComponentService service = new VaadinComponentService();

    @Test
    public void testCreateJsonFromComponent() {
        VerticalLayout supportProfileComponent = new VerticalLayout();
        supportProfileComponent.setId("Support Profile");
        TextField nameField = new TextField("Name");
        nameField.setId("name");
        supportProfileComponent.add(nameField);
        OwnerForm ownerForm = new OwnerForm();
        ownerForm.setId("owner");
        supportProfileComponent.add(ownerForm);

        Map<String, Object> actualJsonMap = service.createJsonFromComponent(supportProfileComponent);

        log.info("JSON: {}", actualJsonMap);

        // Create the expected JSON object
        Map<String, Object> expectedJsonMap = new HashMap<>();
        Map<String, Object> ownerMap = new HashMap<>();
        ownerMap.put("address", "");
        ownerMap.put("ownerName", "");
        Map<String, Object> phoneMap = new HashMap<>();
        phoneMap.put("number", "");
        phoneMap.put("phoneType", "");
        ownerMap.put("phone", phoneMap);
        ownerMap.put("type", "");
        expectedJsonMap.put("owner", ownerMap);
        expectedJsonMap.put("name", "");

        // Assert that the actual JSON map matches the expected JSON map
        assertEquals(expectedJsonMap, actualJsonMap);
    }
}
