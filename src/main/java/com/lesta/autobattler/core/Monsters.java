package com.lesta.autobattler.core;

import com.lesta.autobattler.ability.FireBreath;
import com.lesta.autobattler.ability.SkeletonVulnerability;
import com.lesta.autobattler.ability.SlimeImmunity;
import com.lesta.autobattler.ability.SneakAttack;
import com.lesta.autobattler.ability.StoneSkin;
import com.lesta.autobattler.util.Dice;

import java.util.List;

public final class Monsters {

    private Monsters() {
    }

    public static List<Monster> roster() {
        return List.of(goblin(), skeleton(), slime(), ghost(), golem(), dragon());
    }

    public static Monster random(Dice dice) {
        List<Monster> roster = roster();
        return roster.get(dice.nextInt(roster.size()));
    }

    public static Monster goblin() {
        return new Monster("Гоблин",
                new Attributes(2, 1, 1),
                new Weapon("Ржавый кинжал", 1, DamageType.PIERCING),
                5, Weapons.DAGGER,
                List.of());
    }

    public static Monster skeleton() {
        return new Monster("Скелет",
                new Attributes(2, 2, 2),
                new Weapon("Костяная рука", 1, DamageType.BLUDGEONING),
                10, Weapons.CLUB,
                List.of(new SkeletonVulnerability()));
    }

    public static Monster slime() {
        return new Monster("Слайм",
                new Attributes(1, 3, 1),
                new Weapon("Псевдоподия", 2, DamageType.BLUDGEONING),
                8, Weapons.SPEAR,
                List.of(new SlimeImmunity()));
    }

    public static Monster ghost() {
        return new Monster("Призрак",
                new Attributes(3, 1, 3),
                new Weapon("Призрачное касание", 1, DamageType.SLASHING),
                6, Weapons.SWORD,
                List.of(new SneakAttack()));
    }

    public static Monster golem() {
        return new Monster("Голем",
                new Attributes(1, 3, 1),
                new Weapon("Каменный кулак", 3, DamageType.BLUDGEONING),
                10, Weapons.AXE,
                List.of(new StoneSkin()));
    }

    public static Monster dragon() {
        return new Monster("Дракон",
                new Attributes(4, 3, 3),
                new Weapon("Когти", 3, DamageType.SLASHING),
                20, Weapons.LEGENDARY_SWORD,
                List.of(new FireBreath()));
    }
}
