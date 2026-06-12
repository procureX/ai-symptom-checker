package com.symptomchecker.controller;

import com.symptomchecker.model.SymptomRequest;
import com.symptomchecker.model.SymptomResponse;
import com.symptomchecker.service.SymptomService;

import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class SymptomController {

    private final SymptomService symptomService;
    private static final Logger logger = LoggerFactory.getLogger(SymptomController.class);

    public SymptomController(SymptomService symptomService) {
        this.symptomService = symptomService;
    }

    @PostMapping("/symptoms")
    public SymptomResponse checkSymptoms(public SymptomResponse checkSymptoms(@Valid @RequestBody SymptomRequest request) {
        logger.info("Received symptoms: {}", request.getSymptoms());
        return symptomService.analyzeSymptoms(request);
    }
}