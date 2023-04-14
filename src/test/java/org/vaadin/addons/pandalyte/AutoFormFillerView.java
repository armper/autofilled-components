package org.vaadin.addons.pandalyte;

import org.vaadin.addons.pandalyte.customerOrders.CustomerOrdersView;
import org.vaadin.addons.pandalyte.supportProfile.SupportProfileView;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class AutoFormFillerView extends VerticalLayout {

    public AutoFormFillerView() {
        setWidthFull();

        add(new RouterLink("Support Profile Example", SupportProfileView.class));
        add(new RouterLink("Customer Orders Example", CustomerOrdersView.class));
    }
}
