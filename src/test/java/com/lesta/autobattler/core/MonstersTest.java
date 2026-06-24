package com.lesta.autobattler.core;

import com.lesta.autobattler.ability.Ability;
import com.lesta.autobattler.ability.SkeletonVulnerability;
import com.lesta.autobattler.ability.SlimeImmunity;
import com.lesta.autobattler.ability.SneakAttack;
import com.lesta.autobattler.ability.StoneSkin;
import com.lesta.autobattler.testsupport.ScriptedDice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonstersTest {

    @Test
    @DisplayName("В бестиарии 6 монстров с правильной добычей")
    void rosterHasSixMonstersWithRewards() {
        assertEquals(6, Monsters.roster().size());
        assertEquals(Weapons.DAGGER, Monsters.goblin().reward());
        assertEquals(Weapons.LEGENDARY_SWORD, Monsters.dragon().reward());
    }

    @Test
    @DisplayName("Призрак и Голем переиспользуют способности классов")
    void monstersReuseClassAbilities() {
        assertTrue(hasAbility(Monsters.ghost(), SneakAttack.class), "Призрак — скрытая атака разбойника");
        assertTrue(hasAbility(Monsters.golem(), StoneSkin.class), "Голем — каменная кожа варвара");
        assertTrue(hasAbility(Monsters.skeleton(), SkeletonVulnerability.class));
        assertTrue(hasAbility(Monsters.slime(), SlimeImmunity.class));
        assertTrue(Monsters.goblin().abilities().isEmpty());
    }

    @Test
    @DisplayName("random использует Dice для выбора из бестиария")
    void randomPicksFromRoster() {
        assertEquals("Гоблин", Monsters.random(new ScriptedDice(0)).name());
        assertEquals("Скелет", Monsters.random(new ScriptedDice(1)).name());
    }

    private static boolean hasAbility(Monster monster, Class<? extends Ability> type) {
        return monster.abilities().stream().anyMatch(type::isInstance);
    }
}
