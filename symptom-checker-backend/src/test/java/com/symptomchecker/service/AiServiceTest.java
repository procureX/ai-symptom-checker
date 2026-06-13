package com.symptomchecker.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;

class AiServiceTest {

    private MockWebServer mockWebServer;
    private AiService aiService;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        RestClient restClient = RestClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        aiService = new AiService(restClient);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void testAiAnalysisReturnsJson() throws Exception {
        String mockResponse = """
            {
              "response": "{\\"conditions\\":[\\"Flu\\",\\"Cold\\"], \\"urgency\\":\\"Medium\\"}"
            }
            """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponse)
                .addHeader("Content-Type", "application/json"));

        String result = aiService.getAiAnalysis("fever and cough");

        assertNotNull(result);
        assertTrue(result.contains("conditions"));
        assertTrue(result.contains("urgency"));
    }
}