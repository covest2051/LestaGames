package com.lesta.autobattler.core;

public record Attributes(int strength, int agility, int endurance) {

    public Attributes plusStrength(int delta) {
        return new Attributes(strength + delta, agility, endurance);
    }

    public Attributes plusAgility(int delta) {
        return new Attributes(strength, agility + delta, endurance);
    }

    public Attributes plusEndurance(int delta) {
        return new Attributes(strength, agility, endurance + delta);
    }
}
