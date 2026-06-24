package com.lesta.autobattler.ability;

public final class Poison implements Ability {

    @Override
    public String name() {
        return "Яд";
    }

    @Override
    public String description() {
        return "Дополнительный урон, растущий с каждым ходом: +1 со 2-го хода, +2 с 3-го и далее";
    }

    @Override
    public void onAttack(DamageContext context) {
        int bonus = context.attackerTurn() - 1;
        if (bonus > 0) {
            context.addPure(name(), bonus);
        }
    }
}
