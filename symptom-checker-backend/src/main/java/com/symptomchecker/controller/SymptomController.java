package com.procurex.symptomchecker.controller;

import com.procurex.symptomchecker.model.SymptomRequest;
import com.procurex.symptomchecker.model.SymptomResponse;
import com.procurex.symptomchecker.service.SymptomService;

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
    public SymptomResponse checkSymptoms(@RequestBody SymptomRequest request) {
        logger.info("Received symptoms: {}", request.getSymptoms());
        return symptomService.analyzeSymptoms(request);
    }
}