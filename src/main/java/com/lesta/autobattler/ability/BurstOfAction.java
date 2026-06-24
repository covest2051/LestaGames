package com.lesta.autobattler.ability;

public final class BurstOfAction implements Ability {

    @Override
    public String name() {
        return "Порыв к действию";
    }

    @Override
    public String description() {
        return "В первый ход наносит двойной урон оружием";
    }

    @Override
    public void onAttack(DamageContext context) {
        if (context.attackerTurn() == 1) {
            context.addPure(name(), context.attacker().weapon().damage());
        }
    }
}
