package org.vaadin.addons.mygroup;

import org.vaadin.addons.mygroup.supportProfile.SupportProfileView;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class AutoFormFillerView extends Div {

    public AutoFormFillerView() {
        setWidthFull();

        add(new RouterLink("Support Profile Example", SupportProfileView.class));
    }
}
