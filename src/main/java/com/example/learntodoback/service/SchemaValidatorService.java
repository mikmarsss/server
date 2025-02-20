package com.example.learntodoback.service;

import com.example.learntodoback.dto.schema.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemaValidatorService {

    private final List<SchemaDto> validSchemas = List.of(
            new SchemaDto(
                    List.of(
                            new ComponentDto("1", "battery", List.of("+", "-")),
                            new ComponentDto("2", "led", List.of("+", "-")),
                            new ComponentDto("3", "button", List.of("0", "0"))
                    ),
                    List.of(
                            new ConnectionDto(new ConnectionPoint("1", "+"), new ConnectionPoint("3", "0")),
                            new ConnectionDto(new ConnectionPoint("3", "0"), new ConnectionPoint("2", "+")),
                            new ConnectionDto(new ConnectionPoint("2", "-"), new ConnectionPoint("1", "-"))
                    )
            ),
            new SchemaDto(
                    List.of(
                            new ComponentDto("1", "battery", List.of("+", "-")),
                            new ComponentDto("2", "led", List.of("+", "-"))
                    ),
                    List.of(
                            new ConnectionDto(new ConnectionPoint("1", "+"), new ConnectionPoint("2", "+")),
                            new ConnectionDto(new ConnectionPoint("2", "-"), new ConnectionPoint("1", "-"))
                    )
            ),
            new SchemaDto(
                    List.of(
                            new ComponentDto("1", "bigbattery", List.of("+", "-")),
                            new ComponentDto("2", "fan", List.of("+", "-")),
                            new ComponentDto("3", "button", List.of("0", "0"))
                    ),
                    List.of(
                            new ConnectionDto(new ConnectionPoint("1", "+"), new ConnectionPoint("3", "0")),
                            new ConnectionDto(new ConnectionPoint("3", "0"), new ConnectionPoint("2", "+")),
                            new ConnectionDto(new ConnectionPoint("2", "-"), new ConnectionPoint("1", "-"))
                    )
            )
    );

    public SchemaValidationDto validateSchema(SchemaDto receivedSchema) {
        if (validSchemas.contains(receivedSchema)) {
            return new SchemaValidationDto(true, "Схема верна.");
        } else {
            return new SchemaValidationDto(false, "Получена неверная схема.");
        }
    }
}