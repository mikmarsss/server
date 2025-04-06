package com.example.learntodoback.service;

import com.example.learntodoback.dto.response.ValidationResponseDto;
import com.example.learntodoback.dto.schema.validator.CircuitValidator;
import com.example.learntodoback.dto.schema.ConnectionDto;
import com.example.learntodoback.dto.schema.factory.CircuitValidationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchemaValidatorService {
    private final CircuitValidationFactory validationFactory;

    public ValidationResponseDto validateCircuit(List<ConnectionDto> connections) {
        for (CircuitValidator validator : validationFactory.getValidators()) {
            ValidationResponseDto result = validator.validate(connections);
            if (!result.isValid()) {
                return result;
            }
        }
        return new ValidationResponseDto(true, "Цепь корректна");
    }
}