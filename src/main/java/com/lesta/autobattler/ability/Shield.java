package com.lesta.autobattler.ability;

public final class Shield implements Ability {

    private static final int REDUCTION = 3;

    @Override
    public String name() {
        return "Щит";
    }

    @Override
    public String description() {
        return "−3 к получаемому урону, если сила защищающегося выше силы атакующего";
    }

    @Override
    public void onDefend(DamageContext context) {
        if (context.defender().strength() > context.attacker().strength()) {
            context.addFlatReduction(REDUCTION);
        }
    }
}
