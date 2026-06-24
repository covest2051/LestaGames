package com.lesta.autobattler.ability;

import com.lesta.autobattler.core.DamageType;

public final class SlimeImmunity implements Ability {

    @Override
    public String name() {
        return "Слизистое тело";
    }

    @Override
    public String description() {
        return "Рубящее оружие не наносит урона (но урон от силы и способностей проходит)";
    }

    @Override
    public void onDefend(DamageContext context) {
        context.nullifyType(DamageType.SLASHING);
    }
}
