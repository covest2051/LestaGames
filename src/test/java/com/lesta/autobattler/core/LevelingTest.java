package com.lesta.autobattler.core;

import com.lesta.autobattler.ability.BurstOfAction;
import com.lesta.autobattler.ability.Shield;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LevelingTest {

    @Test
    @DisplayName("Мультикласс: здоровье и атрибуты накапливаются по уровням")
    void multiclassAccumulatesHealthAndStats() {
        // Варвар 1 (END+1 -> 3, HP += 6 + 3 = 9), затем Воин 1 (STR+1 -> 2, HP += 5 + 3 = 8)
        GameCharacter hero = GameCharacter.create("Г",
                new Attributes(1, 1, 2), CharacterClass.BARBARIAN, AbilityMode.TABLE_GATED);
        hero.gainLevel(CharacterClass.WARRIOR);

        assertEquals(2, hero.totalLevel());
        assertEquals(17, hero.maxHealth());
        assertEquals(2, hero.strength());
        assertEquals(1, hero.agility());
        assertEquals(3, hero.endurance());
        assertEquals("Варвар 1 / Воин 1", hero.classDescription());
    }

    @Test
    @DisplayName("TABLE_GATED: способности воина открываются на 2-м и 3-м уровнях")
    void tableGatedUnlocksByLevel() {
        GameCharacter warrior = GameCharacter.create("В",
                new Attributes(1, 1, 1), CharacterClass.WARRIOR, AbilityMode.TABLE_GATED);
        assertTrue(warrior.abilities().isEmpty(), "на 1-м уровне только бонус к силе");

        warrior.gainLevel(CharacterClass.WARRIOR);
        assertEquals(1, warrior.abilities().size());
        assertInstanceOf(Shield.class, warrior.abilities().get(0));

        warrior.gainLevel(CharacterClass.WARRIOR);
        assertEquals(2, warrior.abilities().size());
        assertInstanceOf(BurstOfAction.class, warrior.abilities().get(1));
    }

    @Test
    @DisplayName("ABILITIES_AT_FIRST_LEVEL: весь набор воина доступен сразу")
    void examplesModeUnlocksImmediately() {
        GameCharacter warrior = GameCharacter.create("В",
                new Attributes(1, 1, 1), CharacterClass.WARRIOR, AbilityMode.ABILITIES_AT_FIRST_LEVEL);
        assertEquals(2, warrior.abilities().size());
    }

    @Test
    @DisplayName("Суммарный уровень не превышает 3")
    void totalLevelIsCappedAtThree() {
        GameCharacter hero = GameCharacter.create("Г",
                new Attributes(2, 2, 2), CharacterClass.ROGUE, AbilityMode.TABLE_GATED);
        assertTrue(hero.canLevelUp());
        hero.gainLevel(CharacterClass.WARRIOR);
        assertTrue(hero.canLevelUp());
        hero.gainLevel(CharacterClass.BARBARIAN);
        assertEquals(3, hero.totalLevel());
        assertFalse(hero.canLevelUp());
    }
}
