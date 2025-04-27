package com.example.learntodoback.controller;

import com.example.learntodoback.dto.ValidationRequest;
import com.example.learntodoback.dto.response.ValidationResponseDto;
import com.example.learntodoback.service.SchemaValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schema")
@RequiredArgsConstructor
public class SchemaController {

    private final SchemaValidatorService schemaValidatorService;

    @PostMapping("/validate")
    public ResponseEntity<?> validateSchema(@RequestBody ValidationRequest validationRequest) {
        ValidationResponseDto result = schemaValidatorService.validateCircuit(validationRequest);
        return ResponseEntity.ok(result);
    }
}
