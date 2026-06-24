package com.lesta.autobattler.ability;

import com.lesta.autobattler.core.DamageType;

public final class DamageComponent {

    private final String source;
    private int amount;
    private final DamageType type;

    public DamageComponent(String source, int amount, DamageType type) {
        this.source = source;
        this.amount = amount;
        this.type = type;
    }

    public String source() {
        return source;
    }

    public int amount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public DamageType type() {
        return type;
    }

    public boolean hasType(DamageType candidate) {
        return type == candidate;
    }
}
