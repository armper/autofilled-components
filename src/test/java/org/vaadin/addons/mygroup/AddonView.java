package org.vaadin.addons.mygroup;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class AddonView extends Div {

    public AddonView() {
        VerticalLayout supportProfileComponent = new VerticalLayout();
        supportProfileComponent.setId("Support Profile");
        TextField nameField = new TextField("Name");
        nameField.setId("name");
        supportProfileComponent.add(nameField);
        OwnerForm ownerForm = new OwnerForm();
        ownerForm.setId("owner");
        supportProfileComponent.add(ownerForm);

        Grid<Impact> impactsGrid = new Grid<>(Impact.class);
        impactsGrid.setId("impacts");

        impactsGrid.addItemClickListener(listener -> {
            System.out.println("Clicked on " + listener.getItem());
        });

        Grid<Contact> consumersGrid = new Grid<>(Contact.class);
        consumersGrid.setId("consumers");

        supportProfileComponent.add(impactsGrid, consumersGrid);

        String userPrompt = "I want to create a Support Profile called 'Super Support' with the owner named " +
                "'Jane Doe', living at '1234 Main Street, Springfield', and reachable at '555-1234'. " +
                "For impacts, I'd like to include two. The first impact is named 'System Downtime', " +
                "has a severity of 'High', and the threshold phrase is 'over 30 minutes'. The second " +
                "impact is 'Data Loss', with a severity of 'Medium' and the threshold phrase is 'more " +
                "than 1 GB'. I have 3 consumers. The first consumer is 'Alice' living at '5678 Elm " +
                "Street, Springfield' with phone number '555-6789' and type 'Individual'. The second " +
                "consumer is 'Bob' living at '9101 Maple Street, Springfield' with phone number " +
                "'555-9101' and type 'Business'. The third consumer is 'Carol' living at '11213 Oak " +
                "Street, Springfield' with phone number '555-1121' and type 'Government'.";

        AutoFormFiller theAddon = new AutoFormFiller(supportProfileComponent,
                userPrompt,
                "Fill out N/A in the JSON value if the user did not specify a value.",
                "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");
        theAddon.setId("autoFormFiller");

        add(theAddon);
    }
}
