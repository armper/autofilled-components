package org.vaadin.addons.mygroup.supportProfile;

import org.vaadin.addons.mygroup.supportProfile.model.Contact;
import org.vaadin.addons.mygroup.supportProfile.model.Impact;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class SupportProfileForm extends VerticalLayout {

    public SupportProfileForm() {
        setWidthFull();
        
        setId("Support Profile");

        TextField nameField = new TextField("Name");
        nameField.setId("name");
        add(nameField);

        Checkbox isPublic = new Checkbox("Public");
        isPublic.setId("isPublic");
        add(isPublic);

        OwnerForm ownerForm = new OwnerForm();
        ownerForm.setId("owner");
        add(ownerForm);

        Grid<Impact> impactsGrid = new Grid<>(Impact.class);
        impactsGrid.setId("impacts");

        impactsGrid.addItemClickListener(listener -> {
            System.out.println("Clicked on " + listener.getItem());
        });

        Grid<Contact> consumersGrid = new Grid<>(Contact.class);
        consumersGrid.setId("consumers");

        add(impactsGrid, consumersGrid);
    }
}
