package com.lesta.autobattler.ability;

public interface Ability {

    String name();

    String description();

    default void onAttack(DamageContext context) {
    }

    default void onDefend(DamageContext context) {
    }
}
