package com.yourproject.controller;

import com.yourproject.model.SymptomRequest;
import com.yourproject.model.SymptomResponse;
import com.yourproject.service.SymptomService;

import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class SymptomController {

    private final SymptomService symptomService;
    private static final Logger logger = LoggerFactory.getLogger(SymptomController.class);

    public symptomController(SymptomService symptomService) {
        this.symptomService = symptomService;
    }

    @PostMapping("/symptoms")
    public symptomResponse checkSymptoms(@RequestBody SymptomRequest request) {
        logger.info("Received symptoms: {}", request.getSymptoms());
        return symptomService.analyzeSymptoms(request);
    }
}
