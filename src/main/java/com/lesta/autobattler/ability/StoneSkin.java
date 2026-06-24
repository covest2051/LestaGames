package com.lesta.autobattler.ability;

public final class StoneSkin implements Ability {

    @Override
    public String name() {
        return "Каменная кожа";
    }

    @Override
    public String description() {
        return "Снижает получаемый урон на значение выносливости";
    }

    @Override
    public void onDefend(DamageContext context) {
        context.addFlatReduction(context.defender().endurance());
    }
}
