package com.example.learntodoback.dto.schema.graph;

import com.example.learntodoback.dto.schema.ConnectionDto;
import com.example.learntodoback.enums.Components;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Getter
@Setter
public class GraphDto {

    private Map<String, NodeDto> nodes = new HashMap<>();

    public void addConnection(ConnectionDto connection) {
        Components sourceComponent = Components.fromId(connection.getSourceElementId());
        Components targetComponent = Components.fromId(connection.getTargetElementId());

        NodeDto sourceNode = nodes.computeIfAbsent(
                connection.getUniqueSourceElementId(),
                id -> new NodeDto(id, sourceComponent)
        );

        NodeDto targetNode = nodes.computeIfAbsent(
                connection.getUniqueTargetElementId(),
                id -> new NodeDto(id, targetComponent)
        );

        sourceNode.getEdges().add(new EdgeDto(
                connection.getSourceContact().getId(),
                connection.getTargetContact().getId(),
                targetNode
        ));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (NodeDto node : nodes.values()) {
            sb.append(String.format("Node %s (%s):\n", node.getUniqueId(), node.getComponents().getName()));
            for (EdgeDto edge : node.getEdges()) {
                sb.append("  ").append(edge).append("\n");
            }
        }
        return sb.toString();
    }

    public boolean containsComponent(Predicate<Components> componentPredicate) {
        return nodes.values().stream()
                .anyMatch(node -> componentPredicate.test(node.getComponents()));
    }
}
