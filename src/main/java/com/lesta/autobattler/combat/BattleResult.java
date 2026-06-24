package com.lesta.autobattler.combat;

import com.lesta.autobattler.core.Combatant;

public record BattleResult(Combatant winner, Combatant loser, int totalTurns) {

    public boolean playerWon(Combatant player) {
        return winner == player;
    }
}
