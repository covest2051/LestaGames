package com.lesta.autobattler.core;

public record Weapon(String name, int damage, DamageType type) {

    @Override
    public String toString() {
        return "%s (%d, %s)".formatted(name, damage, type.displayName().toLowerCase());
    }
}
