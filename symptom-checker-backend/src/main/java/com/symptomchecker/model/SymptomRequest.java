package com.symptomchecker.model;

public class SymptomRequest {

    @NotBlank(message = "Symptoms cannot be empty")
    @Size(max = 500, message = "Symptoms too long")
    private String symptoms;

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
}