package com.lesta.autobattler.combat;

import com.lesta.autobattler.core.Combatant;
import com.lesta.autobattler.core.Monster;
import com.lesta.autobattler.core.Monsters;
import com.lesta.autobattler.core.Weapons;
import com.lesta.autobattler.testsupport.RecordingListener;
import com.lesta.autobattler.testsupport.ScriptedDice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.lesta.autobattler.testsupport.Fighters.fighter;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BattleTest {

    @Test
    @DisplayName("Первым ходит тот, у кого выше ловкость; при равенстве — герой")
    void fasterCombatantStrikesFirstTiesGoToPlayer() {
        Combatant fastPlayer = fighter(1, 3, 1, Weapons.SWORD);
        Combatant slowMonster = fighter(1, 1, 1, Weapons.CLUB);
        assertSame(fastPlayer, firstAttacker(fastPlayer, slowMonster));

        Combatant tiePlayer = fighter(1, 2, 1, Weapons.SWORD);
        Combatant tieMonster = fighter(1, 2, 1, Weapons.CLUB);
        assertSame(tiePlayer, firstAttacker(tiePlayer, tieMonster));

        Combatant slowPlayer = fighter(1, 1, 1, Weapons.SWORD);
        Combatant fastMonster = fighter(1, 3, 1, Weapons.CLUB);
        assertSame(fastMonster, firstAttacker(slowPlayer, fastMonster));
    }

    @Test
    @DisplayName("Промах, если бросок ≤ ловкости цели; попадание, если выше")
    void hitMissDependsOnRollVersusDefenderAgility() {
        // Игрок (ловк. 3) бьёт монстра (ловк. 2): сумма 5, промах при броске ≤ 2.
        RecordingListener missed = new RecordingListener();
        new Battle(fighter(1, 3, 1, Weapons.SWORD), fighter(1, 2, 1, Weapons.CLUB),
                new ScriptedDice(2), missed).run();
        assertFalse(missed.events.get(0).hit(), "бросок 2 ≤ ловкости цели 2 — промах");

        RecordingListener landed = new RecordingListener();
        new Battle(fighter(1, 3, 1, Weapons.SWORD), fighter(1, 2, 1, Weapons.CLUB),
                new ScriptedDice(3), landed).run();
        assertTrue(landed.events.get(0).hit(), "бросок 3 > ловкости цели 2 — попадание");
    }

    @Test
    @DisplayName("Сильный герой при гарантированных попаданиях побеждает гоблина")
    void strongPlayerBeatsGoblin() {
        Combatant hero = fighter(20, 5, 3, Weapons.SWORD);
        Monster goblin = Monsters.goblin();

        RecordingListener listener = new RecordingListener();
        BattleResult result = new Battle(hero, goblin, new ScriptedDice(), listener).run();

        assertTrue(result.playerWon(hero));
        assertFalse(goblin.isAlive());
        assertSame(hero, listener.winner);
    }

    private static Combatant firstAttacker(Combatant player, Combatant monster) {
        RecordingListener listener = new RecordingListener();
        new Battle(player, monster, new ScriptedDice(), listener).run();
        return listener.firstAttacker;
    }
}
