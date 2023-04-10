package org.vaadin.addons.mygroup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GptService implements GptServiceProvider {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GptService.class);

    private final GptApi gptApi;
    private static final String MODEL_NAME = "gpt-3.5-turbo-0301";

    public GptService(String apiKey) {
        log.info("Initializing GPT service with API key: {}", apiKey);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            okhttp3.Request request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .build();
            return chain.proceed(request);
        });

        // Set the timeouts (for example, 60 seconds for both)
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.writeTimeout(60, TimeUnit.SECONDS);

        // Optional: Add logging interceptor for debugging
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        gptApi = retrofit.create(GptApi.class);
    }

    public Map<String, Object> callGptApi(String gptRequest) {
        log.info("Calling GPT API with request: {}", gptRequest);

        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", gptRequest);
        messages.add(message);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", MODEL_NAME);
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 1024); // Set the maximum tokens for the response
        requestBody.put("n", 1); // Number of completions to generate for each prompt
        requestBody.put("stop", null); // Set a stop sequence if required

        try {
            Response<Map<String, Object>> response = gptApi.getCompletion(requestBody).execute();
            if (response.isSuccessful()) {
                Map<String, Object> responseBody = response.body();
                // Extract the generated completions
                if (responseBody != null) {
                    // Extract the content from the responseBody
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> choice = choices.get(0);
                        Map<String, Object> gptMessage = (Map<String, Object>) choice.get("message");
                        String content = (String) gptMessage.get("content");

                        // Parse the content into a Map<String, Object>
                        Gson gson = new Gson();
                        Type type = new TypeToken<Map<String, Object>>() {
                        }.getType();
                        Map<String, Object> contentMap = gson.fromJson(content, type);

                        return contentMap;
                    }
                }
            } else {
                log.error("GPT API call failed with status code: {}", response.code());
            }
        } catch (IOException e) {
            log.error("GPT API call failed with exception: {}", e.getMessage());
        }

        return new HashMap<>();
    }

    @Override
    public Map<String, Object> processRequest(String gptRequest) {
        return callGptApi(gptRequest);
    }
}
