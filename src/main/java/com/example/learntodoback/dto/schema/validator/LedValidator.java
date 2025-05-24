package com.example.learntodoback.dto.schema.validator;

import com.example.learntodoback.dto.GraphDto;
import com.example.learntodoback.dto.schema.EdgeDto;
import com.example.learntodoback.dto.schema.NodeDto;
import com.example.learntodoback.enums.Components;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class LedValidator {

    private static final Set<String> NEUTRAL_CONTACTS = Set.of("neutral1", "neutral2", "neutral3", "neutral4");
    public Set<String> getLitLeds(GraphDto graph) {
        NodeDto batteryNode = graph.getNodes().values().stream()
                .filter(n -> n.getComponents().name().startsWith("BATTERY"))
                .findFirst()
                .orElse(null);

        if (batteryNode == null) {
            return Set.of(); // Нет батареи — ничего не может загореться
        }

        Set<String> litLeds = new HashSet<>();

        for (NodeDto ledNode : graph.getNodes().values()) {
            if (ledNode.getComponents() != Components.LED && ledNode.getComponents() != Components.RGB_LED) continue;

            boolean minusConnected = dfsWithContacts(graph, batteryNode, "minus", ledNode, "minus", new HashSet<>());
            if (!minusConnected) continue;

            boolean plusConnected = dfsWithContacts(graph, ledNode, "plus", batteryNode, "plus", new HashSet<>());
            if (!plusConnected) continue;

            litLeds.add(ledNode.getUniqueId());
        }

        return litLeds;
    }

    public boolean isValidCircuit(GraphDto graph) {
        NodeDto batteryNode = graph.getNodes().values().stream()
                .filter(n -> n.getComponents().name().startsWith("BATTERY"))
                .findFirst()
                .orElse(null);

        NodeDto ledNode = graph.getNodes().values().stream()
                .filter(n -> n.getComponents() == Components.LED || n.getComponents() == Components.RGB_LED)
                .findFirst()
                .orElse(null);

        if (batteryNode == null || ledNode == null) {
            return false; // Нет батарейки или LED
        }

        // 1. Найти путь от батареи MINUS к LED MINUS
        boolean minusConnected = dfsWithContacts(graph, batteryNode, "minus", ledNode, "minus", new HashSet<>());

        if (!minusConnected) return false;

        // 2. Найти путь от LED PLUS к батарее PLUS
        boolean plusConnected = dfsWithContacts(graph, ledNode, "plus", batteryNode, "plus", new HashSet<>());

        return plusConnected;
    }

    private boolean dfsWithContacts(GraphDto graph, NodeDto current, String currentContact,
                                    NodeDto target, String targetContact, Set<String> visited) {
        String key = current.getUniqueId() + ":" + currentContact;
        if (visited.contains(key)) return false;
        visited.add(key);

        if (current == target && currentContact.equals(targetContact)) {
            return true;
        }

        // Рассмотрим все ребра из current
        for (EdgeDto edge : current.getEdges()) {
            String edgeSourceContact = edge.getSourceContact();
            String edgeTargetContact = edge.getTargetContact();
            NodeDto nextNode = edge.getTarget();

            // Проверяем разрешён ли переход по контактам:
            if (isContactTransitionAllowed(current, currentContact, edgeSourceContact, edgeTargetContact, nextNode)) {
                if (dfsWithContacts(graph, nextNode, edgeTargetContact, target, targetContact, visited)) {
                    return true;
                }
            }
        }

        // Если не нашли путь по исходящим ребрам, пробуем идти назад (по входящим ребрам)
        for (NodeDto node : graph.getNodes().values()) {
            for (EdgeDto edge : node.getEdges()) {
                if (edge.getTarget() == current) {
                    String edgeSourceContact = edge.getSourceContact();
                    String edgeTargetContact = edge.getTargetContact();
                    NodeDto prevNode = node;

                    // Проверяем разрешён ли переход по контактам в обратную сторону
                    if (isContactTransitionAllowedReverse(prevNode, edgeSourceContact, edgeTargetContact, currentContact)) {
                        if (dfsWithContacts(graph, prevNode, edgeSourceContact, target, targetContact, visited)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean isContactTransitionAllowed(NodeDto currentNode, String currentContact,
                                               String edgeSourceContact, String edgeTargetContact, NodeDto nextNode) {
        // Правила перехода

        if (currentContact.equals(edgeSourceContact)) {
            return true;
        }

        if ((currentNode.getComponents() == Components.LED || currentNode.getComponents() == Components.RGB_LED) &&
                ((currentContact.equals("plus") && edgeSourceContact.equals("minus")) ||
                        (currentContact.equals("minus") && edgeSourceContact.equals("plus")))) {
            return true;
        }

        if (NEUTRAL_CONTACTS.contains(currentContact)) {
            return true; // Нейтральные контакты допускают любые переходы
        }

        return false;
    }

    private boolean isContactTransitionAllowedReverse(NodeDto prevNode, String edgeSourceContact,
                                                      String edgeTargetContact, String currentContact) {
        // Обратный обход — немного проще, проверяем, что переход из edgeSourceContact к currentContact разрешён
        if (edgeTargetContact.equals(currentContact)) {
            return true;
        }

        if ((prevNode.getComponents() == Components.LED || prevNode.getComponents() == Components.RGB_LED) &&
                ((edgeTargetContact.equals("plus") && currentContact.equals("minus")) ||
                        (edgeTargetContact.equals("minus") && currentContact.equals("plus")))) {
            return true;
        }

        if (NEUTRAL_CONTACTS.contains(edgeTargetContact)) {
            return true;
        }

        return false;
    }
}
