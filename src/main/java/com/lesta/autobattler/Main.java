package com.lesta.autobattler;

import com.lesta.autobattler.ui.Ansi;
import com.lesta.autobattler.ui.ConsoleUI;
import com.lesta.autobattler.util.Dice;
import com.lesta.autobattler.util.RandomDice;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        GameConfig config = GameConfig.fromArgs(args);
        Ansi.enabled = config.colorEnabled();

        Dice dice = config.seed() != null ? new RandomDice(config.seed()) : new RandomDice();
        ConsoleUI ui = new ConsoleUI(new Scanner(System.in, StandardCharsets.UTF_8), config);

        new Game(dice, ui, config).run();
    }
}
