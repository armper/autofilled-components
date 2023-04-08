package org.vaadin.addons.mygroup;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Impact {
    private Long id;
    private String name;
    private String severity;
    private String thresholdPhrase;



}
