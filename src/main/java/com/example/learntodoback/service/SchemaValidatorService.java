package com.example.learntodoback.service;

import com.example.learntodoback.dto.ValidationRequest;
import com.example.learntodoback.dto.response.ValidationResponseDto;
import com.example.learntodoback.dto.schema.ConnectionDto;
import com.example.learntodoback.dto.schema.factory.CircuitValidationFactory;
import com.example.learntodoback.dto.schema.validator.CircuitValidator;
import com.example.learntodoback.enums.Components;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.learntodoback.dto.schema.constant.CircuitConstants.DO_ACTION;

@Service
@RequiredArgsConstructor
public class SchemaValidatorService {
    private final CircuitValidationFactory validationFactory;

    public ValidationResponseDto validateCircuit(ValidationRequest validationRequest) {
        List<ConnectionDto> connections = validationRequest.getConnections();
        List<String> componentsId = getComponentsAction(connections);

        for (CircuitValidator validator : validationFactory.getValidators()) {
            boolean result = validator.validate(connections);
            if (!result) {
                return new ValidationResponseDto(
                        false,
                        "Цепь собрана не правильно",
                        new ArrayList<>(),
                        ""
                );
            }
        }

        return new ValidationResponseDto(
                true,
                "Цепь корректна",
                componentsId,
                ""
        );
    }

    public List<String> getComponentsAction(List<ConnectionDto> connections) {
        List<String> uniqueId = new ArrayList<>();

        for (ConnectionDto connectionDto : connections) {
            Components component = Components.fromId(connectionDto.getSourceElementId());
            if (component.getAction().equals(DO_ACTION)) {
                uniqueId.add(connectionDto.getUniqueElementId());
            }
        }
        return uniqueId;
    }
}