package org.vaadin.addons.mygroup;

import org.junit.Test;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class VaadinComponentServiceTest {

    private VaadinComponentService service = new VaadinComponentService();

    @Test
    public void test() {
        VerticalLayout supportProfileComponent = new VerticalLayout();
        supportProfileComponent.setId("Support Profile");
        TextField nameField = new TextField("Name");
        nameField.setId("name");
        supportProfileComponent.add(nameField);
        OwnerForm ownerForm = new OwnerForm();
        ownerForm.setId("owner");
        supportProfileComponent.add(ownerForm);

        var json = service.createJsonFromComponent(supportProfileComponent);

        System.out.println(json);
    }
}
