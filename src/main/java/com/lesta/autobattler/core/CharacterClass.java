package com.lesta.autobattler.core;

import com.lesta.autobattler.ability.Ability;
import com.lesta.autobattler.ability.BurstOfAction;
import com.lesta.autobattler.ability.Poison;
import com.lesta.autobattler.ability.Rage;
import com.lesta.autobattler.ability.Shield;
import com.lesta.autobattler.ability.SneakAttack;
import com.lesta.autobattler.ability.StoneSkin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The three character classes and everything the rules say about them: health per level, starting
 * weapon, the +1 stat granted at the class's first level, and the two abilities from the table
 * (level&nbsp;2 and level&nbsp;3).
 *
 * <p>This enum is the single place where class rules live. The stat bonus always lands at the first
 * class level; the two abilities land either at levels 2 and 3 ({@link AbilityMode#TABLE_GATED}) or
 * both at the first class level ({@link AbilityMode#ABILITIES_AT_FIRST_LEVEL}).</p>
 */
public enum CharacterClass {

    WARRIOR("Воин", 5, Weapons.SWORD, AttributeType.STRENGTH, Shield::new, BurstOfAction::new),
    BARBARIAN("Варвар", 6, Weapons.CLUB, AttributeType.ENDURANCE, StoneSkin::new, Rage::new),
    ROGUE("Разбойник", 4, Weapons.DAGGER, AttributeType.AGILITY, SneakAttack::new, Poison::new);

    private final String displayName;
    private final int healthPerLevel;
    private final Weapon startingWeapon;
    private final AttributeType firstLevelStat;
    private final Supplier<Ability> secondLevelAbility;
    private final Supplier<Ability> thirdLevelAbility;

    CharacterClass(String displayName,
                   int healthPerLevel,
                   Weapon startingWeapon,
                   AttributeType firstLevelStat,
                   Supplier<Ability> secondLevelAbility,
                   Supplier<Ability> thirdLevelAbility) {
        this.displayName = displayName;
        this.healthPerLevel = healthPerLevel;
        this.startingWeapon = startingWeapon;
        this.firstLevelStat = firstLevelStat;
        this.secondLevelAbility = secondLevelAbility;
        this.thirdLevelAbility = thirdLevelAbility;
    }

    public String displayName() {
        return displayName;
    }

    public int healthPerLevel() {
        return healthPerLevel;
    }

    public Weapon startingWeapon() {
        return startingWeapon;
    }

    public AttributeType firstLevelStat() {
        return firstLevelStat;
    }

    public AttributeType statBonusAt(int classLevel) {
        return classLevel == 1 ? firstLevelStat : null;
    }

    public List<Ability> abilitiesGainedAt(int classLevel, AbilityMode mode) {
        List<Ability> gained = new ArrayList<>(2);
        if (mode == AbilityMode.TABLE_GATED) {
            if (classLevel == 2) {
                gained.add(secondLevelAbility.get());
            } else if (classLevel == 3) {
                gained.add(thirdLevelAbility.get());
            }
        } else {
            if (classLevel == 1) {
                gained.add(secondLevelAbility.get());
                gained.add(thirdLevelAbility.get());
            }
        }
        return gained;
    }

    public String abilitiesSummary() {
        return secondLevelAbility.get().name() + ", " + thirdLevelAbility.get().name();
    }
}
