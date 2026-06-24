package com.lesta.autobattler.combat;

import com.lesta.autobattler.ability.Ability;
import com.lesta.autobattler.ability.DamageContext;
import com.lesta.autobattler.core.Combatant;

public final class DamageResolver {

    private DamageResolver() {
    }

    public static DamageContext resolve(Combatant attacker, Combatant defender, int attackerTurn) {
        DamageContext context = new DamageContext(attacker, defender, attackerTurn);
        context.addComponent("Оружие («" + attacker.weapon().name() + "»)",
                attacker.weapon().damage(), attacker.weapon().type());
        context.addPure("Сила", attacker.strength());

        for (Ability ability : attacker.abilities()) {
            ability.onAttack(context);
        }
        for (Ability ability : defender.abilities()) {
            ability.onDefend(context);
        }
        return context;
    }
}
