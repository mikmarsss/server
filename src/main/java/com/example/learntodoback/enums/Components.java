package com.example.learntodoback.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

import static com.example.learntodoback.dto.schema.constant.CircuitConstants.*;

@AllArgsConstructor
@Getter
public enum Components {
    BATTERY_1_5_V(1, "Батарейка 1.5 Вольт", Set.of(PLUS, MINUS)),
    BATTERY_3_0_V(2, "Батарейка 3.0 Вольт", Set.of(PLUS, MINUS)),
    BATTERY_9_0_V(3, "Батарейка 9.0 Вольт", Set.of(PLUS, MINUS)),
    CAPACITOR(4, "Конденсатор", Set.of(NEUTRAL_1, NEUTRAL_2)),
    DIODE(5, "Диод", Set.of(PLUS, MINUS)),
    BUTTON(6, "Кнопка", Set.of(NEUTRAL_1, NEUTRAL_2, NEUTRAL_3, NEUTRAL_4)),
    LED(7, "Светодиод", Set.of(PLUS, MINUS)),
    POTENTIOMETER(8, "Потенциометр", Set.of(NEUTRAL_1, NEUTRAL_2, NEUTRAL_3)),
    RESISTOR(9, "Резистор", Set.of(NEUTRAL_1, NEUTRAL_2)),
    RGB_LED(10, "Цветной светодиод", Set.of(PLUS, MINUS)),
    SWITCHER(11, "Переключатель", Set.of(NEUTRAL_1, NEUTRAL_2, NEUTRAL_3)),
    VIBROGEAR(12, "Вибропривод", Set.of(PLUS, MINUS)),
    PIEZOELECTRIC(13, "Пьезоэлемент", Set.of(PLUS, MINUS)),
    TRANSISTOR(14, "Транзистор", Set.of(NEUTRAL_1, NEUTRAL_2, NEUTRAL_3)),
    PHOTORESISTOR(15, "Фоторезистор", Set.of(NEUTRAL_1, NEUTRAL_2));

    private final int id;
    private final String name;
    private final Set<String> validContacts;

    public static Components fromId(int id) {
        return Arrays.stream(values())
                .filter(c -> c.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown component ID: " + id));
    }
}
