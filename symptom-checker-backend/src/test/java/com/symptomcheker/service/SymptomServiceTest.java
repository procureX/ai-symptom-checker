package com.symptomchecker.service;

import com.symptomchecker.model.SymptomRequest;
import com.symptomchecker.model.SymptomResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymptomServiceTest {

    private final SymptomService symptomService = new SymptomService();

    @Test
    void testAnalyzeSymptomsReturnsDummyData() {
        SymptomRequest request = new SymptomRequest("fever");
        SymptomResponse response = symptomService.analyzeSymptoms(request);

        assertNotNull(response);
        assertEquals("Low", response.getUrgency());
        assertFalse(response.getConditions().isEmpty());
    }

    @Test
    void testAnalyzeSymptomsWithEmptyInput() {
        SymptomRequest request = new SymptomRequest("");
        SymptomResponse response = symptomService.analyzeSymptoms(request);

        assertNotNull(response);
        assertEquals("Low", response.getUrgency());
    }

    @Test
    void testAnalyzeSymptomsWithNullInput() {
        SymptomRequest request = new SymptomRequest(null);
        SymptomResponse response = symptomService.analyzeSymptoms(request);

        assertNotNull(response);
        assertEquals("Low", response.getUrgency());
    }
}