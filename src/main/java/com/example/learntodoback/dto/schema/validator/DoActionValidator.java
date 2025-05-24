package com.example.learntodoback.dto.schema.validator;

import com.example.learntodoback.dto.schema.graph.GraphDto;
import com.example.learntodoback.dto.schema.graph.EdgeDto;
import com.example.learntodoback.dto.schema.graph.NodeDto;
import com.example.learntodoback.enums.Components;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.example.learntodoback.dto.schema.constant.CircuitConstants.*;

@Component
public class DoActionValidator implements CircuitValidator {

    private static final Set<String> NEUTRAL_CONTACTS = Set.of(NEUTRAL_1, NEUTRAL_2, NEUTRAL_3, NEUTRAL_4);

    @Override
    public Set<String> getIds(GraphDto graph) {
        NodeDto batteryNode = graph.getNodes().values().stream()
                .filter(n -> n.getComponents().name().startsWith("BATTERY"))
                .findFirst()
                .orElse(null);

        if (batteryNode == null) {
            return Set.of(); // Нет батареи — ничего не может загореться
        }

        Set<String> litLeds = new HashSet<>();

        for (NodeDto ledNode : graph.getNodes().values()) {
            if (ledNode.getComponents() != Components.PIEZOELECTRIC && ledNode.getComponents() != Components.VIBROGEAR)
                continue;

            boolean minusConnected = dfsWithContacts(graph, batteryNode, MINUS, ledNode, MINUS, new HashSet<>());
            if (!minusConnected) continue;

            boolean plusConnected = dfsWithContacts(graph, ledNode, PLUS, batteryNode, PLUS, new HashSet<>());
            if (!plusConnected) continue;

            litLeds.add(ledNode.getUniqueId());
        }

        return litLeds;
    }

    @Override
    public boolean isValid(GraphDto graph) {
        NodeDto batteryNode = graph.getNodes().values().stream()
                .filter(n -> n.getComponents().name().startsWith("BATTERY"))
                .findFirst()
                .orElse(null);

        NodeDto ledNode = graph.getNodes().values().stream()
                .filter(n -> n.getComponents() == Components.PIEZOELECTRIC || n.getComponents() == Components.VIBROGEAR)
                .findFirst()
                .orElse(null);

        if (batteryNode == null || ledNode == null) {
            return false;
        }

        // 1. Найти путь от батареи MINUS к LED MINUS
        boolean minusConnected = dfsWithContacts(graph, batteryNode, MINUS, ledNode, MINUS, new HashSet<>());

        if (!minusConnected) return false;

        // 2. Найти путь от LED PLUS к батарее PLUS
        boolean plusConnected = dfsWithContacts(graph, ledNode, PLUS, batteryNode, PLUS, new HashSet<>());

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

        if (NEUTRAL_CONTACTS.contains(edgeTargetContact)) {
            return true;
        }

        return false;
    }
}
