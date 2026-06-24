package com.lesta.autobattler.core;

import com.lesta.autobattler.ability.Ability;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCombatant implements Combatant {

    protected int maxHealth;
    protected int currentHealth;
    protected int turnCount;
    protected final List<Ability> abilities = new ArrayList<>();

    @Override
    public int maxHealth() {
        return maxHealth;
    }

    @Override
    public int currentHealth() {
        return currentHealth;
    }

    @Override
    public boolean isAlive() {
        return currentHealth > 0;
    }

    @Override
    public void takeDamage(int amount) {
        currentHealth = Math.max(0, currentHealth - amount);
    }

    @Override
    public void healToFull() {
        currentHealth = maxHealth;
    }

    @Override
    public List<Ability> abilities() {
        return abilities;
    }

    @Override
    public int currentTurn() {
        return turnCount;
    }

    @Override
    public void incrementTurn() {
        turnCount++;
    }

    @Override
    public void resetTurns() {
        turnCount = 0;
    }
}
