package com.lesta.autobattler.combat;

import com.lesta.autobattler.ability.BurstOfAction;
import com.lesta.autobattler.ability.FireBreath;
import com.lesta.autobattler.ability.Poison;
import com.lesta.autobattler.ability.Rage;
import com.lesta.autobattler.ability.Shield;
import com.lesta.autobattler.ability.SkeletonVulnerability;
import com.lesta.autobattler.ability.SlimeImmunity;
import com.lesta.autobattler.ability.SneakAttack;
import com.lesta.autobattler.ability.StoneSkin;
import com.lesta.autobattler.core.Combatant;
import com.lesta.autobattler.core.Weapons;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.lesta.autobattler.testsupport.Fighters.fighter;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DamagePipelineTest {

    private static int dealt(Combatant attacker, Combatant defender, int turn) {
        return DamageResolver.resolve(attacker, defender, turn).finalDamage();
    }

    @Test
    @DisplayName("Базовый урон = урон оружия + сила")
    void baseDamageIsWeaponPlusStrength() {
        Combatant attacker = fighter(2, 1, 1, Weapons.SWORD);
        Combatant defender = fighter(1, 1, 1, Weapons.DAGGER);
        assertEquals(5, dealt(attacker, defender, 1));
    }

    @Test
    @DisplayName("Слайм: рубящее оружие игнорируется, но сила проходит")
    void slimeNullifiesSlashingButStrengthApplies() {
        Combatant attacker = fighter(2, 1, 1, Weapons.SWORD);
        Combatant slime = fighter(1, 3, 1, Weapons.SPEAR, new SlimeImmunity());
        assertEquals(2, dealt(attacker, slime, 1));
    }

    @Test
    @DisplayName("Слайм: «порыв к действию» воина проходит даже рубящим оружием")
    void slimeStillTakesBurstOfAction() {
        Combatant warrior = fighter(2, 1, 1, Weapons.SWORD, new BurstOfAction());
        Combatant slime = fighter(1, 3, 1, Weapons.SPEAR, new SlimeImmunity());
        assertEquals(5, dealt(warrior, slime, 1));
    }

    @Test
    @DisplayName("Скелет: вдвое больше урона от дробящего оружия")
    void skeletonDoublesBludgeoning() {
        Combatant attacker = fighter(2, 2, 2, Weapons.CLUB);
        Combatant skeleton = fighter(2, 2, 2, Weapons.CLUB, new SkeletonVulnerability());
        assertEquals(10, dealt(attacker, skeleton, 1));
    }

    @Test
    @DisplayName("Скелет: не-дробящее оружие урон не удваивает")
    void skeletonNoBonusForNonBludgeoning() {
        Combatant attacker = fighter(2, 2, 2, Weapons.SWORD);
        Combatant skeleton = fighter(2, 2, 2, Weapons.CLUB, new SkeletonVulnerability());
        assertEquals(5, dealt(attacker, skeleton, 1));
    }

    @Test
    @DisplayName("Каменная кожа снижает урон на выносливость")
    void stoneSkinReducesByEndurance() {
        Combatant attacker = fighter(2, 1, 1, Weapons.SWORD);
        Combatant golem = fighter(1, 1, 3, Weapons.AXE, new StoneSkin());
        assertEquals(2, dealt(attacker, golem, 1));
    }

    @Test
    @DisplayName("Щит срабатывает только если защищающийся сильнее")
    void shieldOnlyWhenStronger() {
        Combatant attacker = fighter(2, 1, 1, Weapons.SWORD);
        Combatant stronger = fighter(4, 1, 1, Weapons.SWORD, new Shield());
        Combatant weaker = fighter(1, 1, 1, Weapons.SWORD, new Shield());
        assertEquals(2, dealt(attacker, stronger, 1));
        assertEquals(5, dealt(attacker, weaker, 1));
    }

    @Test
    @DisplayName("Скрытая атака: +1 только когда атакующий ловчее цели")
    void sneakAttackNeedsHigherAgility() {
        Combatant nimble = fighter(1, 3, 1, Weapons.DAGGER, new SneakAttack());
        Combatant slow = fighter(1, 1, 1, Weapons.DAGGER);
        assertEquals(4, dealt(nimble, slow, 1));
        assertEquals(3, dealt(slow, nimble, 1));
    }

    @Test
    @DisplayName("Яд растёт с каждым ходом: +1 со 2-го, +2 с 3-го")
    void poisonRampsByTurn() {
        Combatant rogue = fighter(1, 1, 1, Weapons.DAGGER, new Poison());
        Combatant target = fighter(1, 1, 1, Weapons.DAGGER);
        assertEquals(3, dealt(rogue, target, 1));
        assertEquals(4, dealt(rogue, target, 2));
        assertEquals(5, dealt(rogue, target, 3));
    }

    @Test
    @DisplayName("Ярость: +2 в первые 3 хода, затем −1")
    void rageBonusThenPenalty() {
        Combatant barbarian = fighter(1, 1, 1, Weapons.DAGGER, new Rage());
        Combatant target = fighter(1, 1, 1, Weapons.DAGGER);
        assertEquals(5, dealt(barbarian, target, 1));
        assertEquals(5, dealt(barbarian, target, 3));
        assertEquals(2, dealt(barbarian, target, 4));
    }

    @Test
    @DisplayName("Порыв к действию удваивает урон оружия только в первый ход")
    void burstOfActionFirstTurnOnly() {
        Combatant warrior = fighter(1, 1, 1, Weapons.SWORD, new BurstOfAction());
        Combatant target = fighter(1, 1, 1, Weapons.DAGGER);
        assertEquals(7, dealt(warrior, target, 1));
        assertEquals(4, dealt(warrior, target, 2));
    }

    @Test
    @DisplayName("Огненное дыхание срабатывает каждый 3-й ход")
    void fireBreathEveryThirdTurn() {
        Combatant dragon = fighter(4, 3, 3, Weapons.SWORD, new FireBreath());
        Combatant target = fighter(1, 1, 1, Weapons.DAGGER);
        assertEquals(7, dealt(dragon, target, 1));
        assertEquals(7, dealt(dragon, target, 2));
        assertEquals(10, dealt(dragon, target, 3));
        assertEquals(10, dealt(dragon, target, 6));
    }

    @Test
    @DisplayName("Урон не может быть отрицательным")
    void damageNeverGoesNegative() {
        Combatant weak = fighter(1, 1, 1, Weapons.DAGGER);
        Combatant tank = fighter(1, 1, 5, Weapons.CLUB, new StoneSkin());
        assertEquals(0, dealt(weak, tank, 1));
    }
}
