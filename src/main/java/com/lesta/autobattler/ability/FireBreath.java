package com.lesta.autobattler.ability;

public final class FireBreath implements Ability {

    private static final int EVERY = 3;
    private static final int EXTRA_DAMAGE = 3;

    @Override
    public String name() {
        return "Огненное дыхание";
    }

    @Override
    public String description() {
        return "Каждый 3-й ход наносит дополнительно 3 урона";
    }

    @Override
    public void onAttack(DamageContext context) {
        if (context.attackerTurn() % EVERY == 0) {
            context.addPure(name(), EXTRA_DAMAGE);
        }
    }
}
