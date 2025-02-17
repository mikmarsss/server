package com.example.learntodoback.controller;

import com.example.learntodoback.dto.schema.SchemaDto;
import com.example.learntodoback.dto.schema.SchemaValidationDto;
import com.example.learntodoback.exception.BusinessException;
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
    public ResponseEntity<?> validateSchema(@RequestBody SchemaDto receivedSchema) {
        SchemaValidationDto result = schemaValidatorService.validateSchema(receivedSchema);
        return ResponseEntity.ok(result);
    }
}
