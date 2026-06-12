package com.symptomchecker.integration;

import com.symptomchecker.model.SymptomRequest;
import com.symptomchecker.model.SymptomResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SymptomIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSymptomsEndpointReturnsValidResponse() {
        SymptomRequest request = new SymptomRequest("fever, cough");

        ResponseEntity<SymptomResponse> response =
                restTemplate.postForEntity("/api/symptoms", request, SymptomResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getConditions().isEmpty());
        assertEquals("Low", response.getBody().getUrgency());
    }

    @Test
    void testEmptySymptomsStillReturnsResponse() {
        SymptomRequest request = new SymptomRequest("");

        ResponseEntity<SymptomResponse> response =
                restTemplate.postForEntity("/api/symptoms", request, SymptomResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testMissingFieldStillReturnsResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("{}", headers);

        ResponseEntity<SymptomResponse> response =
                restTemplate.postForEntity("/api/symptoms", entity, SymptomResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}