package com.example.learntodoback.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

import static com.example.learntodoback.dto.schema.constant.CircuitConstants.*;

@AllArgsConstructor
@Getter
public enum Components {
    BATTERY_1_5_V(1, "Батарейка 1.5 Вольт", Set.of(PLUS, MINUS), DONT_DO_ACTION),
    BATTERY_3_0_V(2, "Батарейка 3.0 Вольт", Set.of(PLUS, MINUS), DONT_DO_ACTION),
    BATTERY_9_0_V(3, "Батарейка 9.0 Вольт", Set.of(PLUS, MINUS), DONT_DO_ACTION),
    CAPACITOR(4, "Конденсатор", Set.of(NEUTRAL_1, NEUTRAL_2), DONT_DO_ACTION),
    DIODE(5, "Диод", Set.of(PLUS, MINUS), DONT_DO_ACTION),
    BUTTON(6, "Кнопка", Set.of(NEUTRAL_1, NEUTRAL_2, NEUTRAL_3, NEUTRAL_4), DONT_DO_ACTION),
    LED(7, "Светодиод", Set.of(PLUS, MINUS), DO_ACTION),
    POTENTIOMETER(8, "Потенциометр", Set.of(NEUTRAL_1, NEUTRAL_2, NEUTRAL_3), DONT_DO_ACTION),
    RESISTOR(9, "Резистор", Set.of(NEUTRAL_1, NEUTRAL_2), DONT_DO_ACTION),
    RGB_LED(10, "Цветной светодиод", Set.of(PLUS, MINUS), DO_ACTION),
    SWITCHER(11, "Переключатель", Set.of(NEUTRAL_1, NEUTRAL_2, NEUTRAL_3), DONT_DO_ACTION),
    VIBROGEAR(12, "Вибропривод", Set.of(PLUS, MINUS), DO_ACTION),
    PIEZOELECTRIC(13, "Пьезоэлемент", Set.of(PLUS, MINUS), DO_ACTION),
    TRANSISTOR(14, "Транзистор", Set.of(NEUTRAL_1, NEUTRAL_2, NEUTRAL_3), DONT_DO_ACTION),
    PHOTORESISTOR(15, "Фоторезистор", Set.of(NEUTRAL_1, NEUTRAL_2), DONT_DO_ACTION);

    private final int id;
    private final String name;
    private final Set<String> validContacts;
    private final String action;

    public static Components fromId(int id) {
        return Arrays.stream(values())
                .filter(c -> c.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown component ID: " + id));
    }
}
