package com.example.learntodoback.service;

import com.example.learntodoback.dto.schema.graph.GraphDto;
import com.example.learntodoback.dto.ValidationRequest;
import com.example.learntodoback.dto.response.ValidationResponseDto;
import com.example.learntodoback.dto.schema.ConnectionDto;
import com.example.learntodoback.dto.schema.factory.CircuitValidationFactory;
import com.example.learntodoback.dto.schema.validator.CircuitValidator;
import com.example.learntodoback.dto.schema.validator.DoActionValidator;
import com.example.learntodoback.dto.schema.validator.LedValidator;
import com.example.learntodoback.enums.Components;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SchemaValidatorService {
    private final CircuitValidationFactory validationFactory;

    public ValidationResponseDto validateCircuit(ValidationRequest validationRequest) {
        List<ConnectionDto> connections = validationRequest.getConnections();
        GraphDto graph = new GraphDto();
        connections.forEach(graph::addConnection);
        Set<String> ids = new HashSet<>();

        for (CircuitValidator validator : validationFactory.getValidators()) {
            if (shouldSkipValidator(validator, graph)) {
                continue;
            }
            boolean result = validator.isValid(graph);
            if (!result) {
                return new ValidationResponseDto(
                        false,
                        "Цепь собрана не правильно",
                        new ArrayList<>(),
                        ""
                );
            } else {
                if (validator.getIds(graph) == null) {
                    continue;
                }
                ids.addAll(validator.getIds(graph));
            }
        }

        return new ValidationResponseDto(
                true,
                "Цепь корректна",
                new ArrayList<>(ids),
                ""
        );
    }

    private boolean shouldSkipValidator(CircuitValidator validator, GraphDto graph) {
        if (validator instanceof LedValidator) {
            return !graph.containsComponent(c -> c == Components.LED || c == Components.RGB_LED);
        } else if (validator instanceof DoActionValidator) {
            return !graph.containsComponent(c -> c == Components.PIEZOELECTRIC
                    || c == Components.VIBROGEAR);
        }
        return false;
    }
}