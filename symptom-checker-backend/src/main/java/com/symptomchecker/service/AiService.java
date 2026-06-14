package com.symptomchecker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class AiService {

    private final RestClient restClient;

    public AiService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
    }

    public String getAiAnalysis(String symptoms) {

        Map<String, Object> request = Map.of(
                "model", "medllama2",
                "prompt", buildPrompt(symptoms),
                "stream", false
        );

        Map<String, Object> response = restClient.post()
                .uri("/api/generate")
                .body(request)
                .retrieve()
                .body(Map.class);

        String raw = (String) response.get("response");

        try {
            ObjectMapper mapper = new ObjectMapper();
            
            // raw is a JSON string → parse it
            Map<String, Object> parsed = mapper.readValue(raw, Map.class);
            // return proper JSON
            return mapper.writeValueAsString(parsed);
        } catch (Exception e) {
            // If parsing fails, return fallback JSON
            return """
            {
            "conditions": ["Unable to analyze symptoms"],
            "urgency": "Unknown"
            }
            """;
        }
    }

    private String buildPrompt(String symptoms) {
        return """
        You are a medical symptom analysis assistant.

        Analyze the following symptoms and respond ONLY in valid JSON with this structure:

        {
          "conditions": ["condition1", "condition2"],
          "urgency": "Low | Medium | High"
        }

        Symptoms: %s
        """.formatted(symptoms);
    }
}