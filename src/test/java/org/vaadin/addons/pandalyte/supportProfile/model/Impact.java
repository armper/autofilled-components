package org.vaadin.addons.pandalyte.supportProfile.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Impact {
    private String name;
    private String severity;
    private String threshold;

}
