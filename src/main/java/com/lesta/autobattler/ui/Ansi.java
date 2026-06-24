package com.lesta.autobattler.ui;

public final class Ansi {

    public static boolean enabled = true;

    private static final String RESET = "[0m";
    private static final String BOLD = "[1m";
    private static final String DIM = "[2m";
    private static final String RED = "[31m";
    private static final String GREEN = "[32m";
    private static final String YELLOW = "[33m";
    private static final String BLUE = "[34m";
    private static final String MAGENTA = "[35m";
    private static final String CYAN = "[36m";

    private Ansi() {
    }

    private static String wrap(String text, String code) {
        return enabled ? code + text + RESET : text;
    }

    public static String bold(String text) {
        return wrap(text, BOLD);
    }

    public static String dim(String text) {
        return wrap(text, DIM);
    }

    public static String red(String text) {
        return wrap(text, RED);
    }

    public static String green(String text) {
        return wrap(text, GREEN);
    }

    public static String yellow(String text) {
        return wrap(text, YELLOW);
    }

    public static String blue(String text) {
        return wrap(text, BLUE);
    }

    public static String magenta(String text) {
        return wrap(text, MAGENTA);
    }

    public static String cyan(String text) {
        return wrap(text, CYAN);
    }
}
