package com.symptomchecker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class AiServiceTest {

    private AiService aiService;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        RestClient restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();

        aiService = new AiService(restClient);

        mockServer = MockRestServiceServer.bindTo(restClient).build();
    }

    @Test
    void testAiAnalysisReturnsJson() {
        String mockResponse = """
            {
              "response": "{\\"conditions\\":[\\"Flu\\",\\"Cold\\"], \\"urgency\\":\\"Medium\\"}"
            }
            """;

        mockServer.expect(requestTo("http://localhost:11434/api/generate"))
                .andExpect(method(org.springframework.http.HttpMethod.POST))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));

        String result = aiService.getAiAnalysis("fever and cough");

        assertNotNull(result);
        assertTrue(result.contains("conditions"));
        assertTrue(result.contains("urgency"));

        mockServer.verify();
    }
}