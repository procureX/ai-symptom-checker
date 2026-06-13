package com.symptomchecker.service;

import com.symptomchecker.model.SymptomRequest;
import com.symptomchecker.model.SymptomResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SymptomServiceTest {

    private final AiService aiService = Mockito.mock(AiService.class);
    private final SymptomService symptomService = new SymptomService(aiService);

    @Test
    void testAnalyzeSymptomsReturnsDummyData() {
        SymptomRequest request = new SymptomRequest("fever");

        Mockito.when(aiService.analyzeSymptoms("fever"))
                .thenReturn(new SymptomResponse(
                        List.of("Common cold", "Flu"),
                        "Low"
                ));

        SymptomResponse response = symptomService.analyzeSymptoms(request);

        assertNotNull(response);
        assertEquals("Low", response.getUrgency());
        assertFalse(response.getConditions().isEmpty());
    }

    @Test
    void testAnalyzeSymptomsWithEmptyInput() {
        SymptomRequest request = new SymptomRequest("");

        Mockito.when(aiService.analyzeSymptoms(""))
                .thenReturn(new SymptomResponse(
                        List.of("Common cold", "Flu"),
                        "Low"
                ));

        SymptomResponse response = symptomService.analyzeSymptoms(request);

        assertNotNull(response);
        assertEquals("Low", response.getUrgency());
    }

    @Test
    void testAnalyzeSymptomsWithNullInput() {
        SymptomRequest request = new SymptomRequest(null);

        Mockito.when(aiService.analyzeSymptoms(null))
                .thenReturn(new SymptomResponse(
                        List.of("Common cold", "Flu"),
                        "Low"
                ));

        SymptomResponse response = symptomService.analyzeSymptoms(request);

        assertNotNull(response);
        assertEquals("Low", response.getUrgency());
    }
}