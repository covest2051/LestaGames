package com.lesta.autobattler;

import com.lesta.autobattler.core.AbilityMode;

public final class GameConfig {

    private AbilityMode abilityMode = AbilityMode.TABLE_GATED;
    private boolean colorEnabled = System.getenv("NO_COLOR") == null;
    private int winsToComplete = 5;
    private long turnDelayMillis = 250;
    private Long seed = null;

    public static GameConfig fromArgs(String[] args) {
        GameConfig config = new GameConfig();
        for (String arg : args) {
            String value = arg.contains("=") ? arg.substring(arg.indexOf('=') + 1) : "";
            if (arg.startsWith("--mode=")) {
                config.abilityMode = value.equalsIgnoreCase("examples")
                        ? AbilityMode.ABILITIES_AT_FIRST_LEVEL
                        : AbilityMode.TABLE_GATED;
            } else if (arg.startsWith("--wins=")) {
                config.winsToComplete = Math.max(1, parseIntOrDefault(value, config.winsToComplete));
            } else if (arg.startsWith("--delay=")) {
                config.turnDelayMillis = Math.max(0, parseIntOrDefault(value, (int) config.turnDelayMillis));
            } else if (arg.equals("--fast")) {
                config.turnDelayMillis = 0;
            } else if (arg.equals("--no-color")) {
                config.colorEnabled = false;
            } else if (arg.startsWith("--seed=")) {
                config.seed = parseLongOrNull(value);
            }
        }
        return config;
    }

    private static int parseIntOrDefault(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private static Long parseLongOrNull(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public AbilityMode abilityMode() {
        return abilityMode;
    }

    public boolean colorEnabled() {
        return colorEnabled;
    }

    public int winsToComplete() {
        return winsToComplete;
    }

    public long turnDelayMillis() {
        return turnDelayMillis;
    }

    public Long seed() {
        return seed;
    }
}
