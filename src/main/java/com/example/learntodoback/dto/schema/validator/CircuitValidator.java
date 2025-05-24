package com.example.learntodoback.dto.schema.validator;

import com.example.learntodoback.dto.schema.graph.GraphDto;

import java.util.Set;

public interface CircuitValidator {
    boolean isValid(GraphDto graphDto);
    Set<String> getIds(GraphDto graphDto);
}
