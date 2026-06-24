package com.lesta.autobattler.core;

import com.lesta.autobattler.ability.Ability;

import java.util.List;

public final class Monster extends AbstractCombatant {

    private final String name;
    private final Attributes attributes;
    private final Weapon weapon;
    private final Weapon reward;

    public Monster(String name,
                   Attributes attributes,
                   Weapon weapon,
                   int health,
                   Weapon reward,
                   List<Ability> abilities) {
        this.name = name;
        this.attributes = attributes;
        this.weapon = weapon;
        this.reward = reward;
        this.maxHealth = health;
        this.currentHealth = health;
        this.abilities.addAll(abilities);
    }

    public Weapon reward() {
        return reward;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int strength() {
        return attributes.strength();
    }

    @Override
    public int agility() {
        return attributes.agility();
    }

    @Override
    public int endurance() {
        return attributes.endurance();
    }

    @Override
    public Weapon weapon() {
        return weapon;
    }
}
