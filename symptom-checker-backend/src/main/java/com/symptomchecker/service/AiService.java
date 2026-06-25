package com.symptomchecker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@Service
public class AiService {

    private final RestClient restClient;
    private final ObjectMapper mapper;

    public AiService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
        this.mapper = new ObjectMapper(); // Reuse single instance for performance
    }

    public String getAiAnalysis(String symptoms) {
        // Enforce native JSON format parameter in Ollama request body
        Map<String, Object> requestBody = Map.of(
                "model", "medllama2",
                "prompt", buildPrompt(symptoms),
                "format", "json", // Instructs Ollama to natively constrain output to well-formed JSON
                "stream", false
        );

        try {
            // Retrieve response mapping straight to a Map
            Map<?, ?> response = restClient.post()
                    .uri("/api/generate")
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);

            if (response == null || !response.containsKey("response")) {
                return getFallbackJson();
            }

            // Clean & extract the inner generated text
            String rawTextJson = ((String) response.get("response")).trim();

            // Since Ollama forced valid JSON mode, we safely read it 
            // and write it out straight to ensure exact syntax compliance
            Object parsedJson = mapper.readValue(rawTextJson, Object.class);
            return mapper.writeValueAsString(parsedJson);

        } catch (Exception e) {
            // Log the error locally for debugging your 80% build
            System.err.println("Error parsing MedLlama2 response: " + e.getMessage());
            return getFallbackJson();
        }
    }

    private String buildPrompt(String symptoms) {
        return """
        You are a medical symptom analysis assistant.
        Analyze the given symptoms and respond explicitly in a structured JSON schema.
        
        CRITICAL: Do not include any conversational filler, markdown formatting blocks, or text outside the JSON block.
        
        Expected Schema:
        {
          "conditions": ["condition1", "condition2"],
          "urgency": "Low | Medium | High"
        }

        Symptoms: %s
        """.formatted(symptoms);
    }

    private String getFallbackJson() {
        return """
        {
          "conditions": ["Unable to safely analyze symptoms locally"],
          "urgency": "Unknown"
        }
        """;
    }
}