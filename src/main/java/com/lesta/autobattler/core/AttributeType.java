package com.lesta.autobattler.core;

public enum AttributeType {
    STRENGTH("Сила") {
        @Override public Attributes applyPlusOne(Attributes a) { return a.plusStrength(1); }
    },
    AGILITY("Ловкость") {
        @Override public Attributes applyPlusOne(Attributes a) { return a.plusAgility(1); }
    },
    ENDURANCE("Выносливость") {
        @Override public Attributes applyPlusOne(Attributes a) { return a.plusEndurance(1); }
    };

    private final String displayName;

    AttributeType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    public abstract Attributes applyPlusOne(Attributes a);
}
