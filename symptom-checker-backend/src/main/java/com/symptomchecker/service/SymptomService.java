package com.symptomchecker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.symptomchecker.model.SymptomRequest;
import com.symptomchecker.model.SymptomResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SymptomService {

    private final AiService aiService;
    private final ObjectMapper mapper = new ObjectMapper();

    public SymptomService(AiService aiService) {
        this.aiService = aiService;
    }

    public SymptomResponse analyzeSymptoms(SymptomRequest request) {

        try {
            String aiOutput = aiService.getAiAnalysis(request.getSymptoms());

            Map<String, Object> json = mapper.readValue(aiOutput, Map.class);

            List<String> conditions = (List<String>) json.get("conditions");
            String urgency = (String) json.get("urgency");

            return new SymptomResponse(conditions, urgency);

        } catch (Exception e) {
            return new SymptomResponse(
                    List.of("Unable to analyze symptoms"),
                    "Unknown"
            );
        }
    }
}