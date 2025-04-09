package com.example.learntodoback.dto.schema.validator;

import com.example.learntodoback.dto.response.ValidationResponseDto;
import com.example.learntodoback.dto.schema.ConnectionDto;

import java.util.List;

public interface CircuitValidator {
    ValidationResponseDto validate(List<ConnectionDto> connections);
}
