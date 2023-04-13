package org.vaadin.addons.mygroup.supportProfile;

import org.vaadin.addons.mygroup.supportProfile.model.OwnerType;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class OwnerForm extends VerticalLayout {
    public OwnerForm() {
        super();
        TextField name = new TextField("Name");
        name.setId("ownerName");
        TextField address = new TextField("Address");
        address.setId("address");
        PhoneForm phone = new PhoneForm();
        phone.setId("phone");
        
        ComboBox<OwnerType> type = new ComboBox<>("Type");
        type.setItems(OwnerType.values());
        type.setId("type");

        add(name, address, phone, type);
    }

}
