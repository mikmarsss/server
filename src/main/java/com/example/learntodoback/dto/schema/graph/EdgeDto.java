package com.example.learntodoback.dto.schema.graph;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EdgeDto {
    String sourceContact;
    String targetContact;
    NodeDto target;

    public EdgeDto(String sourceContact, String targetContact, NodeDto target) {
        this.sourceContact = sourceContact;
        this.targetContact = targetContact;
        this.target = target;
    }

    @Override
    public String toString() {
        return String.format(" --[%s->%s]--> %s (%s)",
                sourceContact, targetContact, target.uniqueId, target.components.getName());
    }
}
