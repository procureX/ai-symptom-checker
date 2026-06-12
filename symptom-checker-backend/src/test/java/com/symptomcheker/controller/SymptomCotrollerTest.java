package com.symptomchecker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.symptomchecker.model.SymptomRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SymptomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testValidSymptoms() throws Exception {
        SymptomRequest req = new SymptomRequest("fever, cough");

        mockMvc.perform(post("/api/symptoms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conditions").exists())
                .andExpect(jsonPath("$.urgency").exists());
    }

    @Test
    void testEmptySymptoms() throws Exception {
        SymptomRequest req = new SymptomRequest("");

        mockMvc.perform(post("/api/symptoms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conditions").exists());
    }

    @Test
    void testMissingField() throws Exception {
        mockMvc.perform(post("/api/symptoms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void testInvalidJson() throws Exception {
        mockMvc.perform(post("/api/symptoms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"symptoms\": fever}"))
                .andExpect(status().isBadRequest());
    }
}