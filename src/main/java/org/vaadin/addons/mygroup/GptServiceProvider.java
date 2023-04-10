package org.vaadin.addons.mygroup;

import java.util.Map;

public interface GptServiceProvider {
    /**
     * Call the GPT API with the given input text.
     *
     * @param input The input text for the GPT API.
     * @return A map representing the response from the GPT API.
     */
    Map<String, Object> processRequest(String input);
}
