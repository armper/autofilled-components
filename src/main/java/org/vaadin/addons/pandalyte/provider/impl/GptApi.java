package org.vaadin.addons.pandalyte.provider.impl;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GptApi {
    @Headers("Content-Type: application/json")
    @POST("https://api.openai.com/v1/chat/completions")
    Call<Map<String, Object>> getCompletion(@Body Map<String, Object> requestBody);
}