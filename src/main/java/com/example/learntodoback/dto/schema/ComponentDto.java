package com.example.learntodoback.dto.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDto {
    private String id;
    private String type;
    private List<String> pins;
}
