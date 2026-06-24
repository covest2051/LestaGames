package com.lesta.autobattler.combat;

import com.lesta.autobattler.ability.DamageComponent;
import com.lesta.autobattler.ability.DamageContext;
import com.lesta.autobattler.core.Combatant;

import java.util.List;

public record AttackEvent(
        Combatant attacker,
        Combatant defender,
        int turn,
        boolean hit,
        int roll,
        int agilitySum,
        List<DamageComponent> components,
        double multiplier,
        int flatReduction,
        int finalDamage,
        int defenderHealthAfter,
        int defenderMaxHealth) {

    static AttackEvent miss(Combatant attacker, Combatant defender, int turn, int roll, int agilitySum) {
        return new AttackEvent(attacker, defender, turn, false, roll, agilitySum,
                List.of(), 1.0, 0, 0, defender.currentHealth(), defender.maxHealth());
    }

    static AttackEvent hit(Combatant attacker, Combatant defender, int turn, int roll, int agilitySum,
                           DamageContext context, int finalDamage) {
        return new AttackEvent(attacker, defender, turn, true, roll, agilitySum,
                List.copyOf(context.components()), context.incomingMultiplier(), context.flatReduction(),
                finalDamage, defender.currentHealth(), defender.maxHealth());
    }
}
