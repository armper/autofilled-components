package org.vaadin.addons.mygroup;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class OwnerForm extends VerticalLayout {
    public OwnerForm() {
        super();
        TextField name = new TextField("Name");
        name.setId("name");
        TextField address = new TextField("Address");
        address.setId("address");
        TextField phone = new TextField("Phone");
        phone.setId("phone");
        ComboBox<OwnerType> type = new ComboBox<>("Type");
        type.setItems(OwnerType.values());
        type.setId("type");

        add(name, address, phone, type);
    }

}
