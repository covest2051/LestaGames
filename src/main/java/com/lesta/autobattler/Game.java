package com.lesta.autobattler;

import com.lesta.autobattler.combat.Battle;
import com.lesta.autobattler.combat.BattleResult;
import com.lesta.autobattler.core.Attributes;
import com.lesta.autobattler.core.CharacterClass;
import com.lesta.autobattler.core.GameCharacter;
import com.lesta.autobattler.core.Monster;
import com.lesta.autobattler.core.Monsters;
import com.lesta.autobattler.ui.ConsoleUI;
import com.lesta.autobattler.util.Dice;

public final class Game {

    private enum Outcome {
        COMPLETED,
        DIED
    }

    private final Dice dice;
    private final ConsoleUI ui;
    private final GameConfig config;

    public Game(Dice dice, ConsoleUI ui, GameConfig config) {
        this.dice = dice;
        this.ui = ui;
        this.config = config;
    }

    public void run() {
        ui.printBanner();
        boolean keepPlaying = true;
        while (keepPlaying) {
            GameCharacter hero = createHero();
            Outcome outcome = playSession(hero);
            keepPlaying = outcome == Outcome.COMPLETED
                    ? ui.askYesNo("Сыграть ещё раз новым героем?")
                    : ui.askYesNo("Создать нового персонажа и попробовать снова?");
        }
        ui.goodbye();
    }

    private GameCharacter createHero() {
        String name = ui.askName();
        Attributes rolled = new Attributes(dice.roll(1, 3), dice.roll(1, 3), dice.roll(1, 3));
        ui.showRolledAttributes(rolled);
        CharacterClass chosen = ui.chooseClass();
        GameCharacter hero = GameCharacter.create(name, rolled, chosen, config.abilityMode());
        ui.showCharacterSheet(hero);
        return hero;
    }

    private Outcome playSession(GameCharacter hero) {
        int wins = 0;
        int battleNumber = 1;
        while (true) {
            Monster monster = Monsters.random(dice);
            ui.announceMonster(monster, battleNumber, config.winsToComplete());

            BattleResult result = new Battle(hero, monster, dice, ui).run();

            if (!result.playerWon(hero)) {
                ui.showDefeat(hero, monster, wins);
                return Outcome.DIED;
            }

            wins++;
            ui.showStreak(wins, config.winsToComplete());
            if (wins >= config.winsToComplete()) {
                ui.showVictory(hero, wins);
                return Outcome.COMPLETED;
            }

            afterWin(hero, monster);
            battleNumber++;
        }
    }

    private void afterWin(GameCharacter hero, Monster monster) {
        if (ui.askWeaponSwap(hero.weapon(), monster.reward())) {
            hero.swapWeapon(monster.reward());
        }
        if (hero.canLevelUp()) {
            CharacterClass chosen = ui.chooseLevelUpClass(hero);
            hero.gainLevel(chosen);
        } else {
            ui.note("Достигнут максимальный суммарный уровень (3) — здоровье просто восстановлено.");
        }
        hero.healToFull();
        ui.showCharacterSheet(hero);
    }
}
