package com.example.learntodoback.dto.schema.validator;

import com.example.learntodoback.dto.response.ValidationResponseDto;
import com.example.learntodoback.dto.schema.ConnectionDto;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CircuitClosureValidator implements CircuitValidator {

    @Override
    public ValidationResponseDto validate(List<ConnectionDto> connections) {
        if (connections.isEmpty()) {
            return new ValidationResponseDto(false, "Список соединений пуст");
        }

        Map<Integer, List<Integer>> graph = buildGraph(connections);

        if (!isGraphConnected(graph) || !hasAllEvenDegrees(graph)) {
            return new ValidationResponseDto(false, "Цепь не замкнута");
        }

        return new ValidationResponseDto(true, "Цепь замкнута");
    }

    private static Map<Integer, List<Integer>> buildGraph(List<ConnectionDto> connections) {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (ConnectionDto conn : connections) {
            graph.computeIfAbsent(conn.getSourceElementId(), k -> new ArrayList<>())
                    .add(conn.getTargetElementId());
            graph.computeIfAbsent(conn.getTargetElementId(), k -> new ArrayList<>())
                    .add(conn.getSourceElementId());
        }

        return graph;
    }

    private static boolean isGraphConnected(Map<Integer, List<Integer>> graph) {
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

    private static boolean hasAllEvenDegrees(Map<Integer, List<Integer>> graph) {
        return graph.values().stream()
                .allMatch(neighbors -> neighbors.size() % 2 == 0);
    }
}
