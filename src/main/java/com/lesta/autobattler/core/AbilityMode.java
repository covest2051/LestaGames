package com.lesta.autobattler.core;

public enum AbilityMode {

    TABLE_GATED("по таблице (способности открываются на 2-м и 3-м уровнях)"),

    ABILITIES_AT_FIRST_LEVEL("как в примерах (весь набор способностей с 1-го уровня класса)");

    private final String displayName;

    AbilityMode(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
