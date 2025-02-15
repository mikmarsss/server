package com.example.learntodoback.dto.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchemaDto {
    private List<ComponentDto> components;
    private List<ConnectionDto> connections;
}
