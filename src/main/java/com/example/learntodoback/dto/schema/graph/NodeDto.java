package com.example.learntodoback.dto.schema.graph;

import com.example.learntodoback.enums.Components;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NodeDto {
    String uniqueId;
    Components components;
    List<EdgeDto> edges = new ArrayList<>();

    public NodeDto(String uniqueId, Components components) {
        this.uniqueId = uniqueId;
        this.components = components;
    }
}
