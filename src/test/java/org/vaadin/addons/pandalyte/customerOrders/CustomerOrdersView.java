package org.vaadin.addons.pandalyte.customerOrders;

import org.vaadin.addons.pandalyte.AutoFormFiller;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("customer-orders-example")
public class CustomerOrdersView extends VerticalLayout {

    public CustomerOrdersView() {
        setWidthFull();

        // read apiKey from environment variable
        String apiKey = System.getProperty("GPT_API_KEY");

        FormLayout customerOrdersForm = new FormLayout();
        TextField nameField = new TextField("Name");
        nameField.setId("name");
        customerOrdersForm.add(nameField);
        TextField addressField = new TextField("Address");
        addressField.setId("address");
        customerOrdersForm.add(addressField);
        TextField phoneField = new TextField("Phone");
        phoneField.setId("phone");

        customerOrdersForm.add(phoneField);
        TextField emailField = new TextField("Email");
        emailField.setId("email");
        customerOrdersForm.add(emailField);

        Grid<Order> orderGrid = new Grid<>(Order.class);
        orderGrid.removeAllColumns();
        orderGrid.addColumn(Order::getOrderNumber).setHeader("Order Number");
        orderGrid.addColumn(Order::getItemName).setHeader("Item Name");
        orderGrid.addColumn(Order::getOrderDate).setHeader("Order Date");
        orderGrid.addColumn(Order::getOrderStatus).setHeader("Order Status");
        orderGrid.addColumn(Order::getOrderTotal).setHeader("Order Total");
        orderGrid.setId("orders");

        customerOrdersForm.add(orderGrid);

        AutoFormFiller autoFormFiller = new AutoFormFiller(customerOrdersForm, apiKey);
        autoFormFiller.setId("supportProfile");

        add(autoFormFiller);

        String userPrompt = "I want to create a customer 'John Smith' who lives at "
                + "'1234 Elm Street, Springfield' and can be reached at phone number '555-1234' and "
                + "email 'johnsmith@example.com'. John has placed 3 orders. The first order "
                + "(Order Number 1001) contains 2 items of 'Smartphone' for a total of $1000, placed on "
                + "'2023-01-10' with a status of 'Delivered'. The second order (Order Number 1002) "
                + "includes 1 item of 'Laptop' with a total of $1500, placed on '2023-02-15' with a "
                + "status of 'In Transit'. The third order (Order Number 1003) consists of 5 items of "
                + "'Wireless Headphones' for a total of $500, placed on '2023-03-20' with a status of "
                + "'Cancelled'. Fill out the customer details and orders in the form.";

        add(new Button("Process", event -> {
            autoFormFiller.autoFill(userPrompt,
                    "Fill out N/A in the JSON value if the user did not specify a value. "
                            + "Map 'total' to 'orderTotal' as a Double. "
                            + "Map 'status' to 'orderStatus'. "
                            + "Map 'orderNumber' to a string. "
                            + "Map 'item' to 'itemName'.");
        }));

    }
}
