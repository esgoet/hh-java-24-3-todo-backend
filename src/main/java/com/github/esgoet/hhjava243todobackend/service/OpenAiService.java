package com.github.esgoet.hhjava243todobackend.service;

import com.github.esgoet.hhjava243todobackend.model.OpenAiRequest;
import com.github.esgoet.hhjava243todobackend.model.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenAiService {
    private final RestClient client;

    public OpenAiService(@Value("${OPEN_AI_URL}") String baseUrl,
                         @Value("${OPEN_AI_KEY}") String key) {
        client = RestClient.builder()
                .baseUrl(baseUrl)
                //setzt Header für alle Anfragen --> Prinzip = Key, Value
                .defaultHeader("Authorization", "Bearer " + key)
                .build();
    }


    public String reviseInput(String input) {
        OpenAiRequest request = new OpenAiRequest("Return the following with correct spelling and grammar. Capitalize the first letter, don't put a dot in the end: " + input,
                 false);

        OpenAiResponse response = client.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(OpenAiResponse.class);
        return response.getAnswer();
    }
}
