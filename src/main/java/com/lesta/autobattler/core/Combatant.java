package com.lesta.autobattler.core;

import com.lesta.autobattler.ability.Ability;

import java.util.List;

public interface Combatant {

    String name();

    int strength();

    int agility();

    int endurance();

    Weapon weapon();

    int maxHealth();

    int currentHealth();

    boolean isAlive();

    void takeDamage(int amount);

    void healToFull();

    List<Ability> abilities();

    int currentTurn();

    void incrementTurn();

    void resetTurns();
}
