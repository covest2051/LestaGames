package com.lesta.autobattler.util;

public interface Dice {

    int nextInt(int bound);

    default int roll(int minInclusive, int maxInclusive) {
        return minInclusive + nextInt(maxInclusive - minInclusive + 1);
    }
}
