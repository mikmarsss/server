package com.example.learntodoback.dto.schema;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ComponentConnectionTransformer {
    public List<ComponentConnectionDto> transform(List<ConnectionDto> connections) {
        List<ComponentConnectionDto> result = new ArrayList<>();

        Map<Integer, List<ConnectionDto>> incomingMap = new HashMap<>();
        for (ConnectionDto conn : connections) {
            incomingMap
                    .computeIfAbsent(conn.getTargetElementId(), k -> new ArrayList<>())
                    .add(conn);
        }

        for (ConnectionDto conn : connections) {
            int sourceElementId = conn.getSourceElementId();
            int targetElementId = conn.getTargetElementId();

            ComponentConnectionDto dto = new ComponentConnectionDto(sourceElementId);
            dto.setComponentId(conn.getUniqueElementId());
            dto.setSourceField(conn.getSourceContactId());
            dto.setTargetField(conn.getTargetContactId());
            dto.setTargetElementId(targetElementId);

            List<ConnectionDto> incomingToSource = incomingMap.getOrDefault(sourceElementId, Collections.emptyList());
            if (!incomingToSource.isEmpty()) {
                ConnectionDto in = incomingToSource.getFirst();
                dto.setSourceOutputField(in.getTargetContactId());
                dto.setTargetOutputElementId(in.getSourceElementId());

                List<ConnectionDto> incomingToTarget = incomingMap.getOrDefault(targetElementId, Collections.emptyList());
                for (ConnectionDto inTarget : incomingToTarget) {
                    if (inTarget.getSourceElementId() == in.getSourceElementId()) {
                        dto.setTargetOutputField(inTarget.getTargetContactId());
                        break;
                    }
                }
            }

            result.add(dto);
        }

        return result;
    }
}