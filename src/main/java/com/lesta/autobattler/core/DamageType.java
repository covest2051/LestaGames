package com.lesta.autobattler.core;

public enum DamageType {
    SLASHING("Рубящий"),
    PIERCING("Колющий"),
    BLUDGEONING("Дробящий");

    private final String displayName;

    DamageType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
