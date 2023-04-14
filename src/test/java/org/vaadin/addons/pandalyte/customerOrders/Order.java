package org.vaadin.addons.pandalyte.customerOrders;

import lombok.Data;

@Data
public class Order {
    private String orderNumber;
    private String itemName;
    private String orderDate;
    private String orderStatus;
    private Double orderTotal;
}
