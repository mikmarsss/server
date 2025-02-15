package com.example.learntodoback.dto.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class ConnectionPoint {
    private String component;
    private String pin;
}