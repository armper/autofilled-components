package org.vaadin.addons.mygroup;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VaadinComponentInfo {
    private final String id;
    private final String type;
    private final Component component;
    private final List<VaadinComponentInfo> children = new ArrayList<>();

    public void addChild(VaadinComponentInfo child) {
        children.add(child);
    }
}
