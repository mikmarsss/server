package com.example.learntodoback.dto.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComponentConnectionDto {
    private String uniqueElementId;
    private int elementId;
    private String sourceField;
    private String targetField;
    private String sourceOutputField;
    private String targetOutputField;
    private int targetElementId;
    private int targetOutputElementId;

    public ComponentConnectionDto(int elementId) {
        this.elementId = elementId;
    }
}