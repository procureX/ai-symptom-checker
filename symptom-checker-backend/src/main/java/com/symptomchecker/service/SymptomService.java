package com.symptomchecker.service;

import com.symptomchecker.model.SymptomRequest;
import com.symptomchecker.model.SymptomResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymptomService {

    private final AiService aiService;

    public SymptomService(AiService aiService) {
        this.aiService = aiService;
    }

    public SymptomResponse analyzeSymptoms(SymptomRequest request) {

        String aiOutput = aiService.getAiAnalysis(request.getSymptoms());

        return new SymptomResponse(
                List.of("Common cold", "Flu"),
                "Low"
        );
    }
}