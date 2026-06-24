package com.lesta.autobattler.testsupport;

import com.lesta.autobattler.ability.Ability;
import com.lesta.autobattler.core.Attributes;
import com.lesta.autobattler.core.Combatant;
import com.lesta.autobattler.core.Monster;
import com.lesta.autobattler.core.Weapon;
import com.lesta.autobattler.core.Weapons;

import java.util.List;

public final class Fighters {

    private Fighters() {
    }

    public static Combatant fighter(int strength, int agility, int endurance, Weapon weapon, Ability... abilities) {
        return fighter(100, strength, agility, endurance, weapon, abilities);
    }

    public static Combatant fighter(int health, int strength, int agility, int endurance,
                                    Weapon weapon, Ability... abilities) {
        return new Monster("Боец",
                new Attributes(strength, agility, endurance),
                weapon, health, Weapons.DAGGER, List.of(abilities));
    }
}
