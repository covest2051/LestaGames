package com.lesta.autobattler.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AttributesTest {

    @Test
    @DisplayName("Мутаторы возвращают новый объект и не меняют исходный")
    void mutatorsAreImmutable() {
        Attributes base = new Attributes(1, 2, 3);

        assertEquals(new Attributes(2, 2, 3), base.plusStrength(1));
        assertEquals(new Attributes(1, 4, 3), base.plusAgility(2));
        assertEquals(new Attributes(1, 2, 6), base.plusEndurance(3));

        assertEquals(new Attributes(1, 2, 3), base, "исходный объект не изменился");
    }
}
