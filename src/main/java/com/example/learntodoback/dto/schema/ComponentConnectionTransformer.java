package com.example.learntodoback.dto.schema;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ComponentConnectionTransformer {

    public List<ComponentConnectionDto> transform(List<ConnectionDto> connections) {
        List<ComponentConnectionDto> result = new ArrayList<>();
        Map<String, List<ConnectionDto>> incomingMap = new HashMap<>();

        // Группируем входящие соединения по targetElementId
        for (ConnectionDto conn : connections) {
            if (!incomingMap.containsKey(conn.getUniqueTargetElementId())) {
                incomingMap
                        .computeIfAbsent(conn.getUniqueTargetElementId(), k -> new ArrayList<>())
                        .add(conn);
            } else {
                String uniqueSourceId = conn.getUniqueSourceElementId();
                conn.setUniqueSourceElementId(conn.getUniqueTargetElementId());
                conn.setUniqueTargetElementId(uniqueSourceId);
                int sourceElementId = conn.getSourceElementId();
                conn.setSourceElementId(conn.getTargetElementId());
                conn.setTargetElementId(sourceElementId);
                String sourceContactId = conn.getSourceContact().getId();
                conn.getSourceContact().setId(conn.getTargetContact().getId());
                conn.getTargetContact().setId(sourceContactId);

                incomingMap
                        .computeIfAbsent(conn.getUniqueTargetElementId(), k -> new ArrayList<>())
                        .add(conn);
            }

        }

        for (ConnectionDto conn : connections) {
            int sourceElementId = conn.getSourceElementId();
            int targetElementId = conn.getTargetElementId();

            ComponentConnectionDto dto = new ComponentConnectionDto(sourceElementId);
            dto.setUniqueElementId(conn.getUniqueSourceElementId());
            dto.setSourceField(conn.getSourceContact().getId());
            dto.setTargetField(conn.getTargetContact().getId());
            dto.setTargetElementId(targetElementId);

            List<ConnectionDto> incomingToSource = incomingMap.getOrDefault(dto.getUniqueElementId(), Collections.emptyList());
            if (!incomingToSource.isEmpty()) {
                ConnectionDto in = incomingToSource.getFirst();
                dto.setSourceOutputField(in.getTargetContact().getId());
                dto.setTargetOutputElementId(in.getSourceElementId());
                dto.setTargetOutputField(in.getSourceContact().getId());
            }

            result.add(dto);
        }

        return result;
    }
}