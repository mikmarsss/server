package com.example.learntodoback.dto.schema.validator;

import com.example.learntodoback.dto.schema.graph.GraphDto;
import com.example.learntodoback.dto.schema.graph.EdgeDto;
import com.example.learntodoback.dto.schema.graph.NodeDto;
import com.example.learntodoback.enums.Components;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.learntodoback.dto.schema.constant.CircuitConstants.*;

@Component
public class ButtonValidator {

    private static final Map<String, Set<String>> BUTTON_TRANSITIONS = Map.of(
            NEUTRAL_1, Set.of(NEUTRAL_2, NEUTRAL_4),
            NEUTRAL_2, Set.of(NEUTRAL_1, NEUTRAL_3),
            NEUTRAL_3, Set.of(NEUTRAL_2, NEUTRAL_4),
            NEUTRAL_4, Set.of(NEUTRAL_1, NEUTRAL_3)
    );
    public boolean validateButtonConnections(GraphDto graph) {
        // Группируем все соединения по уникальному ID кнопки
        Map<String, List<EdgeDto>> buttonEdges = graph.getNodes().values().stream()
                .filter(node -> node.getComponents() == Components.BUTTON)
                .collect(Collectors.toMap(
                        NodeDto::getUniqueId,
                        NodeDto::getEdges
                ));

        for (Map.Entry<String, List<EdgeDto>> entry : buttonEdges.entrySet()) {
            String buttonId = entry.getKey();
            List<EdgeDto> edges = entry.getValue();

            // Для каждой кнопки проверяем её соединения
            if (!validateSingleButtonConnections(edges)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateSingleButtonConnections(List<EdgeDto> edges) {
        // Собираем все переходы для данной кнопки
        Map<String, Set<String>> actualTransitions = new HashMap<>();

        for (EdgeDto edge : edges) {
            String source = edge.getSourceContact().toLowerCase();
            String target = edge.getTargetContact().toLowerCase();

            // Добавляем переход в нашу карту фактических соединений
            actualTransitions.computeIfAbsent(source, k -> new HashSet<>()).add(target);
        }

        // Проверяем, что все фактические переходы разрешены
        for (Map.Entry<String, Set<String>> entry : actualTransitions.entrySet()) {
            String sourceContact = entry.getKey();
            Set<String> actualTargets = entry.getValue();

            // Получаем разрешенные переходы для данного контакта
            Set<String> allowedTargets = BUTTON_TRANSITIONS.getOrDefault(sourceContact, Collections.emptySet());

            // Проверяем, что все фактические переходы разрешены
            for (String target : actualTargets) {
                if (!allowedTargets.contains(target)) {
                    return false;
                }
            }

            // Дополнительно проверяем, что кнопка соединена правильно (только один переход)
            if (actualTargets.size() > 1) {
                return false;
            }
        }

        return true;
    }
}
