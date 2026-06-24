package com.lesta.autobattler.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class GameCharacter extends AbstractCombatant {

    private final String name;
    private final AbilityMode abilityMode;
    private final Map<CharacterClass, Integer> classLevels = new LinkedHashMap<>();

    private Attributes attributes;
    private Weapon weapon;

    private GameCharacter(String name, Attributes rolledAttributes, AbilityMode abilityMode) {
        this.name = name;
        this.attributes = rolledAttributes;
        this.abilityMode = abilityMode;
    }

    public static GameCharacter create(String name,
                                       Attributes rolledAttributes,
                                       CharacterClass initialClass,
                                       AbilityMode abilityMode) {
        GameCharacter hero = new GameCharacter(name, rolledAttributes, abilityMode);
        hero.weapon = initialClass.startingWeapon();
        hero.gainLevel(initialClass);
        hero.healToFull();
        return hero;
    }

    public void gainLevel(CharacterClass characterClass) {
        int newClassLevel = classLevels.merge(characterClass, 1, Integer::sum);

        AttributeType statBonus = characterClass.statBonusAt(newClassLevel);
        if (statBonus != null) {
            attributes = statBonus.applyPlusOne(attributes);
        }

        maxHealth += characterClass.healthPerLevel() + attributes.endurance();
        abilities.addAll(characterClass.abilitiesGainedAt(newClassLevel, abilityMode));
    }

    public int totalLevel() {
        return classLevels.values().stream().mapToInt(Integer::intValue).sum();
    }

    public boolean canLevelUp() {
        return totalLevel() < 3;
    }

    public int levelIn(CharacterClass characterClass) {
        return classLevels.getOrDefault(characterClass, 0);
    }

    public Map<CharacterClass, Integer> classLevels() {
        return Collections.unmodifiableMap(classLevels);
    }

    public void swapWeapon(Weapon newWeapon) {
        this.weapon = newWeapon;
    }

    public Attributes attributes() {
        return attributes;
    }

    public AbilityMode abilityMode() {
        return abilityMode;
    }

    public String classDescription() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<CharacterClass, Integer> entry : classLevels.entrySet()) {
            if (!builder.isEmpty()) {
                builder.append(" / ");
            }
            builder.append(entry.getKey().displayName()).append(' ').append(entry.getValue());
        }
        return builder.toString();
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
