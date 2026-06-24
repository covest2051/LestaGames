package com.lesta.autobattler.combat;

import com.lesta.autobattler.core.Combatant;

public interface BattleListener {

    default void onBattleStart(Combatant player, Combatant monster, Combatant firstAttacker) {
    }

    default void onAttack(AttackEvent event) {
    }

    default void onBattleEnd(Combatant winner) {
    }

    BattleListener NOOP = new BattleListener() {
    };
}
