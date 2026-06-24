package com.lesta.autobattler.core;

import static com.lesta.autobattler.core.DamageType.BLUDGEONING;
import static com.lesta.autobattler.core.DamageType.PIERCING;
import static com.lesta.autobattler.core.DamageType.SLASHING;

public final class Weapons {

    public static final Weapon SWORD           = new Weapon("Меч", 3, SLASHING);
    public static final Weapon CLUB            = new Weapon("Дубина", 3, BLUDGEONING);
    public static final Weapon DAGGER          = new Weapon("Кинжал", 2, PIERCING);
    public static final Weapon AXE             = new Weapon("Топор", 4, SLASHING);
    public static final Weapon SPEAR           = new Weapon("Копьё", 3, PIERCING);
    public static final Weapon LEGENDARY_SWORD = new Weapon("Легендарный меч", 10, SLASHING);

    private Weapons() {
    }
}
