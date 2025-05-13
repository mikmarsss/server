package com.example.learntodoback.dto.schema;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ComponentConnectionTransformer {

    public List<ComponentConnectionDto> transform(List<ConnectionDto> connections) {
        List<ComponentConnectionDto> result = new ArrayList<>();
        Map<String, List<ConnectionDto>> incomingMap = new HashMap<>();
        boolean hasDuplicates = connections.stream()
                .map(ConnectionDto::getUniqueSourceElementId)
                .distinct()
                .count() < connections.size();
        for (ConnectionDto conn : connections) {
            if (!incomingMap.containsKey(conn.getUniqueSourceElementId())) {
                incomingMap
                        .computeIfAbsent(conn.getUniqueSourceElementId(), k -> new ArrayList<>())
                        .add(conn);
            } else {
                String uniqueSourceId = conn.getUniqueTargetElementId();
                conn.setUniqueTargetElementId(conn.getUniqueSourceElementId());
                conn.setUniqueSourceElementId(uniqueSourceId);
                int sourceElementId = conn.getTargetElementId();
                conn.setTargetElementId(conn.getSourceElementId());
                conn.setSourceElementId(sourceElementId);
                String sourceContactId = conn.getTargetContact().getId();
                conn.getTargetContact().setId(conn.getSourceContact().getId());
                conn.getSourceContact().setId(sourceContactId);

                incomingMap
                        .computeIfAbsent(conn.getUniqueSourceElementId(), k -> new ArrayList<>())
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
            List<ConnectionDto> incomingToTarget = new ArrayList<>();
            if (incomingMap.values().stream()
                    .flatMap(List::stream)
                    .anyMatch(connectionDto -> dto.getUniqueElementId().equals(connectionDto.getUniqueTargetElementId()))) {

                incomingToTarget = incomingMap.values().stream()
                        .flatMap(List::stream)
                        .filter(connectionDto -> dto.getUniqueElementId().equals(connectionDto.getUniqueTargetElementId()))
                        .toList();
            }

            if (!incomingToTarget.isEmpty() && !hasDuplicates) {
                ConnectionDto in = incomingToTarget.getFirst();
                dto.setSourceOutputField(in.getTargetContact().getId());
                dto.setTargetOutputElementId(in.getSourceElementId());
                dto.setTargetOutputField(in.getSourceContact().getId());
            } else {
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

/**
 * if (!incomingMap.containsKey(conn.getUniqueSourceElementId())) {
 * incomingMap
 * .computeIfAbsent(conn.getUniqueSourceElementId(), k -> new ArrayList<>())
 * .add(conn);
 * } else {
 * String uniqueSourceId = conn.getUniqueTargetElementId();
 * conn.setUniqueTargetElementId(conn.getUniqueSourceElementId());
 * conn.setUniqueSourceElementId(uniqueSourceId);
 * int sourceElementId = conn.getTargetElementId();
 * conn.setTargetElementId(conn.getSourceElementId());
 * conn.setSourceElementId(sourceElementId);
 * String sourceContactId = conn.getTargetContact().getId();
 * conn.getTargetContact().setId(conn.getSourceContact().getId());
 * conn.getSourceContact().setId(sourceContactId);
 * <p>
 * incomingMap
 * .computeIfAbsent(conn.getUniqueSourceElementId(), k -> new ArrayList<>())
 * .add(conn);
 * }
 */