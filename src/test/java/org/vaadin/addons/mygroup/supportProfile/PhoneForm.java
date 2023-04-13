package org.vaadin.addons.mygroup.supportProfile;

import org.vaadin.addons.mygroup.supportProfile.model.PhoneType;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class PhoneForm extends VerticalLayout{
    public PhoneForm() {
        TextField number = new TextField("Phone");
        number.setId("number");
        ComboBox<PhoneType> type = new ComboBox<>("Type");
        type.setItems(PhoneType.values());
        type.setId("phoneType");

        add(number, type);
    }
}
