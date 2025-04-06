package com.example.learntodoback.dto.schema.validator;

import com.example.learntodoback.dto.response.ValidationResponseDto;
import com.example.learntodoback.dto.schema.ComponentConnectionDto;
import com.example.learntodoback.dto.schema.ComponentConnectionTransformer;
import com.example.learntodoback.dto.schema.ConnectionDto;
import com.example.learntodoback.enums.Components;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.example.learntodoback.dto.schema.constant.CircuitConstants.*;
import static com.example.learntodoback.enums.Components.LED;
import static com.example.learntodoback.enums.Components.RGB_LED;

@Component
@RequiredArgsConstructor
public class ComponentConnectionValidator implements CircuitValidator {
    private final ComponentConnectionTransformer connectionTransformer;

    @Override
    public ValidationResponseDto validate(List<ConnectionDto> connections) {
        List<ComponentConnectionDto> components = connectionTransformer.transform(connections);

        for (ComponentConnectionDto dto : components) {
            Components component = Components.fromId(dto.getElementId());

            switch (component) {
                case BATTERY_1_5_V:
                case BATTERY_3_0_V:
                case BATTERY_9_0_V:
                    if (!validateBattery(dto)) {
                        return new ValidationResponseDto(false, "Неверное соединение для батарейки: " + component.getName());
                    }
                    break;
                case LED:
                case RGB_LED:
                    if (!validateLed(dto, components)) {
                        return new ValidationResponseDto(false, "Неверное соединение для светодиода: " + component.getName());
                    }
                    break;
                case CAPACITOR:
                case RESISTOR:
                    if (!validateTwoNeutral(dto)) {
                        return new ValidationResponseDto(false, "Неверное соединение для нейтрального компонента: " + component.getName());
                    }
                    break;
                case BUTTON:
                    if (!validateButton(dto)) {
                        return new ValidationResponseDto(false, "Неверное соединение для кнопки " + component.getName());
                    }
                    break;
            }
        }

        return new ValidationResponseDto(true, "Компоненты соединены корректно");
    }

    private boolean validateBattery(ComponentConnectionDto dto) {
        return isValidPinFlow(dto.getSourceField(), dto.getTargetField()) &&
                isValidPinFlow(dto.getSourceOutputField(), dto.getTargetOutputField());
    }

    private boolean validateLed(ComponentConnectionDto dto, List<ComponentConnectionDto> allComponents) {
        Components sourceType = Components.fromId(dto.getElementId());

        boolean isNormalFlow = isValidPinFlow(dto.getSourceField(), dto.getTargetField()) &&
                isValidPinFlow(dto.getSourceOutputField(), dto.getTargetOutputField());

        if (!Set.of(LED, RGB_LED).contains(sourceType)) {
            return isNormalFlow;
        }

        Optional<ComponentConnectionDto> targetOpt = allComponents.stream()
                .filter(c -> c.getElementId() == dto.getTargetElementId())
                .findFirst();

        if (targetOpt.isPresent()) {
            ComponentConnectionDto targetDto = targetOpt.get();
            Components targetType = Components.fromId(targetDto.getElementId());

            if (Set.of(LED, RGB_LED).contains(targetType)) {
                return (
                        (dto.getSourceField().equals(PLUS) && dto.getTargetField().equals(MINUS)) ||
                                (dto.getSourceField().equals(MINUS) && dto.getTargetField().equals(PLUS)) ||
                                isNeutral(dto.getTargetField())
                );
            }
        }

        return isNormalFlow;
    }

    private boolean validateTwoNeutral(ComponentConnectionDto dto) {
        return isNeutral(dto.getSourceField()) && isNeutral(dto.getSourceOutputField());
    }

    private boolean isValidPinFlow(String source, String target) {
        if (source == null || target == null) return true;

        return switch (source) {
            case PLUS -> target.equals(PLUS) || isNeutral(target);
            case MINUS -> target.equals(MINUS) || isNeutral(target);
            default -> isNeutral(source) && (isNeutral(target) || target.equals(PLUS) || target.equals(MINUS));
        };
    }

    private boolean isNeutral(String pin) {
        return pin != null && pin.startsWith("neutral");
    }

    private boolean validateButton(ComponentConnectionDto dto) {
        String sourceField = dto.getSourceField();
        String sourceOutputField = dto.getSourceOutputField();
        Map<String, Set<String>> allowedTransitions = Map.of(
                NEUTRAL_1, Set.of(NEUTRAL_2, NEUTRAL_4),
                NEUTRAL_2, Set.of(NEUTRAL_1, NEUTRAL_3),
                NEUTRAL_3, Set.of(NEUTRAL_2, NEUTRAL_4),
                NEUTRAL_4, Set.of(NEUTRAL_1, NEUTRAL_3)
        );

        if (!allowedTransitions.getOrDefault(sourceField, Set.of()).contains(sourceOutputField)) {
            return false;
        }

        return validateTwoNeutral(dto);
    }
}