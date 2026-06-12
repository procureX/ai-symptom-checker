package com.symptomchecker.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SymptomRequest {

    @NotBlank(message = "Symptoms cannot be empty")
    @Size(max = 500, message = "Symptoms too long")
    private String symptoms;

    public SymptomRequest() {} //o‑args constructor needed for JSON deserialization

    public SymptomRequest(String symptoms) {
        this.symptoms = symptoms;
    }
    
    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
}