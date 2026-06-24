package com.lesta.autobattler.core;

import com.lesta.autobattler.combat.DamageResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.lesta.autobattler.testsupport.Fighters.fighter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CharacterCreationTest {

    @Test
    @DisplayName("Пример из ТЗ: разбойник STR1/AGI3/END2 → 6 HP, кинжал, 3 урона")
    void reproducesTaskExample() {
        GameCharacter rogue = GameCharacter.create("Пример",
                new Attributes(1, 3, 2), CharacterClass.ROGUE, AbilityMode.TABLE_GATED);

        assertEquals(6, rogue.maxHealth(), "4 за уровень разбойника + 2 от выносливости");
        assertEquals(Weapons.DAGGER, rogue.weapon());
        assertEquals(1, rogue.strength());
        assertEquals(4, rogue.agility(), "разбойник на 1-м уровне даёт Ловкость +1");
        assertEquals(2, rogue.endurance());

        Combatant dummy = fighter(1, 1, 1, Weapons.DAGGER);
        assertEquals(3, DamageResolver.resolve(rogue, dummy, 1).finalDamage(),
                "2 от кинжала + 1 от силы");
    }

    @Nested
    @DisplayName("Режим TABLE_GATED")
    class TableGated {

        @Test
        @DisplayName("Разбойник 1-го уровня ещё не имеет способностей")
        void freshRogueHasNoAbilities() {
            GameCharacter rogue = GameCharacter.create("Т",
                    new Attributes(1, 3, 2), CharacterClass.ROGUE, AbilityMode.TABLE_GATED);
            assertTrue(rogue.abilities().isEmpty());
        }
    }

    @Nested
    @DisplayName("Режим ABILITIES_AT_FIRST_LEVEL")
    class AbilitiesAtFirstLevel {

        @Test
        @DisplayName("Разбойник 1-го уровня сразу имеет «Скрытую атаку» и «Яд»")
        void freshRogueHasFullKit() {
            GameCharacter rogue = GameCharacter.create("Т",
                    new Attributes(1, 3, 2), CharacterClass.ROGUE, AbilityMode.ABILITIES_AT_FIRST_LEVEL);
            assertEquals(2, rogue.abilities().size());
        }

        @Test
        @DisplayName("«+1 урона, если цель менее ловкая» работает, как в примере ТЗ")
        void sneakAttackMatchesExampleProse() {
            GameCharacter rogue = GameCharacter.create("Т",
                    new Attributes(1, 3, 2), CharacterClass.ROGUE, AbilityMode.ABILITIES_AT_FIRST_LEVEL);
            Combatant lessAgile = fighter(1, 1, 1, Weapons.DAGGER);
            assertEquals(4, DamageResolver.resolve(rogue, lessAgile, 1).finalDamage());
        }
    }
}
