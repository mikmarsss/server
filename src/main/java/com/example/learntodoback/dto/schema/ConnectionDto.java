package com.example.learntodoback.dto.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDto {
    private ConnectionPoint from;
    private ConnectionPoint to;
}
