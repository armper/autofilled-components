package org.vaadin.addons.pandalyte.provider.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GptRequest {
    private String model;
    private List<Message> messages;
    @JsonProperty("max_tokens")
    private int maxTokens;
    private int n;
    private Object stop;
    private double temperature;

    public GptRequest(String model, List<Message> messages, int maxTokens, int n, Object stop, double temperature) {
        this.model = model;
        this.messages = messages;
        this.maxTokens = maxTokens;
        this.n = n;
        this.stop = stop;
        this.temperature = temperature;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public Object getStop() {
        return stop;
    }

    public void setStop(Object stop) {
        this.stop = stop;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

}
