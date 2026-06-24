package com.lesta.autobattler.util;

import java.util.Random;

public final class RandomDice implements Dice {

    private final Random random;

    public RandomDice() {
        this.random = new Random();
    }

    public RandomDice(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
