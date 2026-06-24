package com.lesta.autobattler.testsupport;

import com.lesta.autobattler.combat.AttackEvent;
import com.lesta.autobattler.combat.BattleListener;
import com.lesta.autobattler.core.Combatant;

import java.util.ArrayList;
import java.util.List;

public final class RecordingListener implements BattleListener {

    public Combatant firstAttacker;
    public Combatant winner;
    public final List<AttackEvent> events = new ArrayList<>();

    @Override
    public void onBattleStart(Combatant player, Combatant monster, Combatant firstAttacker) {
        this.firstAttacker = firstAttacker;
    }

    @Override
    public void onAttack(AttackEvent event) {
        events.add(event);
    }

    @Override
    public void onBattleEnd(Combatant winner) {
        this.winner = winner;
    }
}
