package org.vaadin.addons.mygroup;

import lombok.Data;

@Data
public class Contact {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String type;
}
