package com.example.learntodoback.controller;

import com.example.learntodoback.dto.response.ValidationResponseDto;
import com.example.learntodoback.dto.schema.ConnectionDto;
import com.example.learntodoback.service.SchemaValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/schema")
@RequiredArgsConstructor
public class SchemaController {

    private final SchemaValidatorService schemaValidatorService;

    @PostMapping("/validate")
    public ResponseEntity<?> validateSchema(@RequestBody List<ConnectionDto> receivedSchema) {
        ValidationResponseDto result = schemaValidatorService.validateCircuit(receivedSchema);
        return ResponseEntity.ok(result);
    }
}
