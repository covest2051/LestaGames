package com.lesta.autobattler.testsupport;

import com.lesta.autobattler.util.Dice;

import java.util.ArrayDeque;
import java.util.Deque;

public final class ScriptedDice implements Dice {

    private final Deque<Integer> values = new ArrayDeque<>();

    public ScriptedDice(int... scripted) {
        for (int value : scripted) {
            values.add(value);
        }
    }

    @Override
    public int nextInt(int bound) {
        int value = values.isEmpty() ? bound - 1 : values.poll();
        return Math.floorMod(value, bound);
    }

    @Override
    public int roll(int minInclusive, int maxInclusive) {
        int value = values.isEmpty() ? maxInclusive : values.poll();
        return Math.max(minInclusive, Math.min(maxInclusive, value));
    }
}
