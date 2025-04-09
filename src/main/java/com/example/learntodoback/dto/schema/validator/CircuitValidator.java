package com.example.learntodoback.dto.schema.validator;

import com.example.learntodoback.dto.schema.ConnectionDto;

import java.util.List;

public interface CircuitValidator {
    boolean validate(List<ConnectionDto> connections);
}
