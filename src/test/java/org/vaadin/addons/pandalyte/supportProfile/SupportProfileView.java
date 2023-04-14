package org.vaadin.addons.pandalyte.supportProfile;

import org.vaadin.addons.pandalyte.AutoFormFiller;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("support-profile-example")
public class SupportProfileView extends VerticalLayout {

    private SupportProfileForm supportProfileForm;

    public SupportProfileView() {
        setWidthFull();

        // read apiKey from environment variable
        String apiKey = System.getProperty("GPT_API_KEY");

        supportProfileForm = new SupportProfileForm();

        String userPrompt = "I want to create a public Support Profile called 'Super Support' with the owner named " +
                "'Jane Doe', a person, living at '1234 Main Street, Springfield', and reachable at mobile phone '505-555-1234'. " +
                "For impacts, I'd like to include two. The first impact is named 'System Downtime', " +
                "has a severity of 'High', and the threshold  is 'over 30 minutes'. The second " +
                "impact is 'Data Loss', with a severity of 'Medium' and the threshold phrase is 'more " +
                "than 1 GB'. I have 3 consumers. The first consumer is 'Alice' living at '5678 Elm " +
                "Street, Springfield' with phone number '555-6789' and type 'Individual'. The second " +
                "consumer is 'Bob' living at '9101 Maple Street, Springfield' with phone number " +
                "'555-9101' and type 'Business'. The third consumer is 'Carol' living at '11213 Oak " +
                "Street, Springfield' with phone number '555-1121' and type 'Government'.";

        AutoFormFiller autoFormFiller = new AutoFormFiller(supportProfileForm, apiKey);
        autoFormFiller.setId("supportProfile");

        add(autoFormFiller);

        add(new Button("Process", event -> {
            autoFormFiller.autoFill(userPrompt,
                    "Fill out N/A in the JSON value if the user did not specify a value. If the owner is a person, set the owner type to INDIVIDUAL in all caps. Set the value of phoneType to all caps.");
        }));
    }

}
