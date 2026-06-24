package com.lesta.autobattler.ability;

public final class Rage implements Ability {

    private static final int FURIOUS_TURNS = 3;

    @Override
    public String name() {
        return "Ярость";
    }

    @Override
    public String description() {
        return "+2 к урону в первые 3 хода, затем −1 к урону";
    }

    @Override
    public void onAttack(DamageContext context) {
        int bonus = context.attackerTurn() <= FURIOUS_TURNS ? 2 : -1;
        context.addPure(name(), bonus);
    }
}
