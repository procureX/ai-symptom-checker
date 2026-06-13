package com.symptomchecker.service;

import com.symptomchecker.model.SymptomRequest;
import com.symptomchecker.model.SymptomResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SymptomServiceTest {

    @Test
    void testAnalyzeSymptomsParsesAiJsonCorrectly() throws Exception {
        AiService aiService = mock(AiService.class);

        String aiJson = """
            {
              "conditions": ["Flu", "Cold"],
              "urgency": "Medium"
            }
            """;

        when(aiService.getAiAnalysis("fever and cough")).thenReturn(aiJson);

        SymptomService service = new SymptomService(aiService);

        SymptomRequest request = new SymptomRequest("fever and cough");
        SymptomResponse response = service.analyzeSymptoms(request);

        assertEquals(List.of("Flu", "Cold"), response.getConditions());
        assertEquals("Medium", response.getUrgency());
    }

    @Test
    void testAnalyzeSymptomsHandlesAiFailure() throws Exception {
        AiService aiService = mock(AiService.class);

        when(aiService.getAiAnalysis(anyString())).thenThrow(new RuntimeException("AI error"));

        SymptomService service = new SymptomService(aiService);

        SymptomRequest request = new SymptomRequest("anything");
        SymptomResponse response = service.analyzeSymptoms(request);

        assertEquals(List.of("Unable to analyze symptoms"), response.getConditions());
        assertEquals("Unknown", response.getUrgency());
    }
}