package com.lesta.autobattler.ability;

public final class SneakAttack implements Ability {

    @Override
    public String name() {
        return "Скрытая атака";
    }

    @Override
    public String description() {
        return "+1 к урону, если ловкость атакующего выше ловкости цели";
    }

    @Override
    public void onAttack(DamageContext context) {
        if (context.attacker().agility() > context.defender().agility()) {
            context.addPure(name(), 1);
        }
    }
}
