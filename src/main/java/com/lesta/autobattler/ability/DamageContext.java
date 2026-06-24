package com.lesta.autobattler.ability;

import com.lesta.autobattler.core.Combatant;
import com.lesta.autobattler.core.DamageType;

import java.util.ArrayList;
import java.util.List;

public final class DamageContext {

    private final Combatant attacker;
    private final Combatant defender;
    private final int attackerTurn;

    private final List<DamageComponent> components = new ArrayList<>();
    private double incomingMultiplier = 1.0;
    private int flatReduction = 0;

    public DamageContext(Combatant attacker, Combatant defender, int attackerTurn) {
        this.attacker = attacker;
        this.defender = defender;
        this.attackerTurn = attackerTurn;
    }

    public Combatant attacker() {
        return attacker;
    }

    public Combatant defender() {
        return defender;
    }

    public int attackerTurn() {
        return attackerTurn;
    }

    public void addComponent(String source, int amount, DamageType type) {
        components.add(new DamageComponent(source, amount, type));
    }

    public void addPure(String source, int amount) {
        addComponent(source, amount, null);
    }

    public void nullifyType(DamageType type) {
        for (DamageComponent component : components) {
            if (component.hasType(type)) {
                component.setAmount(0);
            }
        }
    }

    public void addFlatReduction(int amount) {
        flatReduction += amount;
    }

    public void multiplyIncoming(double factor) {
        incomingMultiplier *= factor;
    }

    public List<DamageComponent> components() {
        return components;
    }

    public double incomingMultiplier() {
        return incomingMultiplier;
    }

    public int flatReduction() {
        return flatReduction;
    }

    public int rawTotal() {
        int sum = 0;
        for (DamageComponent component : components) {
            sum += component.amount();
        }
        return sum;
    }

    public int finalDamage() {
        long scaled = Math.round(rawTotal() * incomingMultiplier) - flatReduction;
        return (int) Math.max(0, scaled);
    }
}
