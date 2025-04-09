package com.example.learntodoback.dto.schema.validator;

import com.example.learntodoback.dto.schema.ConnectionDto;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CircuitClosureValidator implements CircuitValidator {
    @Override
    public boolean validate(List<ConnectionDto> connections) {
        boolean isValid = true;

        if (connections.isEmpty()) {
            return false;
        }

        Map<Integer, List<Integer>> graph = buildGraph(connections);

        if (!isGraphConnected(graph)) {
            isValid = false;
        }
        if (!hasAllEvenDegrees(graph)) {
            isValid = false;
        }

        return isValid;
    }

    private Map<Integer, List<Integer>> buildGraph(List<ConnectionDto> connections) {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (ConnectionDto conn : connections) {
            graph.computeIfAbsent(conn.getSourceElementId(), k -> new ArrayList<>())
                    .add(conn.getTargetElementId());
            graph.computeIfAbsent(conn.getTargetElementId(), k -> new ArrayList<>())
                    .add(conn.getSourceElementId());
        }

        return graph;
    }

    private boolean isGraphConnected(Map<Integer, List<Integer>> graph) {
        if (graph.isEmpty()) return false;

        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();
        Integer start = graph.keySet().iterator().next();
        stack.push(start);

        while (!stack.isEmpty()) {
            Integer node = stack.pop();
            if (visited.add(node)) {
                for (Integer neighbor : graph.get(node)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return visited.size() == graph.size();
    }

    private boolean hasAllEvenDegrees(Map<Integer, List<Integer>> graph) {
        return graph.values().stream()
                .allMatch(neighbors -> neighbors.size() % 2 == 0);
    }
}
