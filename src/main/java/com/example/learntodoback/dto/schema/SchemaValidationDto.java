package com.example.learntodoback.dto.schema;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchemaValidationDto {
    private boolean valid;
    private String message;
}
