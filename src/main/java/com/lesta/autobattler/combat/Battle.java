package com.lesta.autobattler.combat;

import com.lesta.autobattler.ability.DamageContext;
import com.lesta.autobattler.core.Combatant;
import com.lesta.autobattler.util.Dice;

public final class Battle {

    private static final int MAX_TURNS = 10_000;

    private final Combatant player;
    private final Combatant monster;
    private final Dice dice;
    private final BattleListener listener;

    public Battle(Combatant player, Combatant monster, Dice dice, BattleListener listener) {
        this.player = player;
        this.monster = monster;
        this.dice = dice;
        this.listener = listener;
    }

    public BattleResult run() {
        player.healToFull();
        monster.healToFull();
        player.resetTurns();
        monster.resetTurns();

        Combatant attacker;
        Combatant defender;
        if (player.agility() >= monster.agility()) { // ties favour the player
            attacker = player;
            defender = monster;
        } else {
            attacker = monster;
            defender = player;
        }
        listener.onBattleStart(player, monster, attacker);

        int turns = 0;
        while (player.isAlive() && monster.isAlive() && turns < MAX_TURNS) {
            attacker.incrementTurn();
            listener.onAttack(performAttack(attacker, defender));
            turns++;

            Combatant next = defender;
            defender = attacker;
            attacker = next;
        }

        Combatant winner = decideWinner();
        listener.onBattleEnd(winner);
        return new BattleResult(winner, winner == player ? monster : player, turns);
    }

    private AttackEvent performAttack(Combatant attacker, Combatant defender) {
        int turn = attacker.currentTurn();
        int agilitySum = attacker.agility() + defender.agility();
        int roll = dice.roll(1, agilitySum);

        if (roll <= defender.agility()) {
            return AttackEvent.miss(attacker, defender, turn, roll, agilitySum);
        }

        DamageContext context = DamageResolver.resolve(attacker, defender, turn);
        int damage = context.finalDamage();
        if (damage > 0) {
            defender.takeDamage(damage);
        }
        return AttackEvent.hit(attacker, defender, turn, roll, agilitySum, context, damage);
    }

    private Combatant decideWinner() {
        if (!monster.isAlive()) {
            return player;
        }
        if (!player.isAlive()) {
            return monster;
        }
        double playerFraction = (double) player.currentHealth() / player.maxHealth();
        double monsterFraction = (double) monster.currentHealth() / monster.maxHealth();
        return playerFraction >= monsterFraction ? player : monster;
    }
}
