package com.example.learntodoback.dto.schema.validator;

import com.example.learntodoback.dto.schema.graph.GraphDto;
import com.example.learntodoback.dto.schema.graph.EdgeDto;
import com.example.learntodoback.dto.schema.graph.NodeDto;
import com.example.learntodoback.enums.Components;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CircuitClosureValidator implements CircuitValidator{
    @Override
    public Set<String> getIds(GraphDto graphDto){
        return null;
    }

    @Override
    public boolean isValid(GraphDto graph) {
        Map<NodeDto, Set<String>> batteryContacts = new HashMap<>();

        for (NodeDto node : graph.getNodes().values()) {
            if (isBattery(node.getComponents())) {
                batteryContacts.put(node, new HashSet<>());
            }
        }

        for (NodeDto node : graph.getNodes().values()) {
            for (EdgeDto edge : node.getEdges()) {
                if (isBattery(node.getComponents())) {
                    batteryContacts.get(node).add(edge.getSourceContact());
                }
            }

            for (NodeDto potentialBattery : batteryContacts.keySet()) {
                for (EdgeDto edge : node.getEdges()) {
                    if (edge.getTarget().equals(potentialBattery)) {
                        batteryContacts.get(potentialBattery).add(edge.getTargetContact());
                    }
                }
            }
        }

        return batteryContacts.values().stream()
                .allMatch(contacts -> contacts.contains("plus") && contacts.contains("minus"));
    }

    private static boolean isBattery(Components component) {
        return component == Components.BATTERY_1_5_V ||
                component == Components.BATTERY_3_0_V ||
                component == Components.BATTERY_9_0_V;
    }
}
