package com.lesta.autobattler.ui;

import com.lesta.autobattler.GameConfig;
import com.lesta.autobattler.ability.DamageComponent;
import com.lesta.autobattler.combat.AttackEvent;
import com.lesta.autobattler.combat.BattleListener;
import com.lesta.autobattler.core.Attributes;
import com.lesta.autobattler.core.AttributeType;
import com.lesta.autobattler.core.CharacterClass;
import com.lesta.autobattler.core.Combatant;
import com.lesta.autobattler.core.GameCharacter;
import com.lesta.autobattler.core.Monster;
import com.lesta.autobattler.core.Weapon;

import java.util.Scanner;
import java.util.StringJoiner;

public final class ConsoleUI implements BattleListener {

    private static final int BAR_WIDTH = 10;

    private final Scanner scanner;
    private final GameConfig config;

    public ConsoleUI(Scanner scanner, GameConfig config) {
        this.scanner = scanner;
        this.config = config;
    }

    public void printBanner() {
        println();
        println(Ansi.bold(Ansi.cyan("╔═══════════════════════════════════════════════════╗")));
        println(Ansi.bold(Ansi.cyan("║          П О Ш А Г О В Ы Й   А В Т О Б О Й        ║")));
        println(Ansi.bold(Ansi.cyan("║              автобаттлер · прототип               ║")));
        println(Ansi.bold(Ansi.cyan("╚═══════════════════════════════════════════════════╝")));
        println(Ansi.dim("  Создайте героя, прокачайте его — а бои пройдут сами."));
        println(Ansi.dim("  Режим способностей: " + config.abilityMode().displayName()));
        println();
    }

    public String askName() {
        print(Ansi.yellow("Как зовут вашего героя? ") + Ansi.dim("(Enter — «Герой»): "));
        String line = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        println();
        return line.isEmpty() ? "Герой" : line;
    }

    public void note(String text) {
        println(Ansi.dim(text));
        println();
    }

    public void showRolledAttributes(Attributes attributes) {
        println(Ansi.yellow("Брошены кости судьбы! Стартовые атрибуты вашего героя:"));
        println("  " + Ansi.bold("Сила:        ") + attributes.strength()
                + Ansi.dim("  (прибавляется к урону оружия)"));
        println("  " + Ansi.bold("Ловкость:    ") + attributes.agility()
                + Ansi.dim("  (шанс попасть и уклониться)"));
        println("  " + Ansi.bold("Выносливость:") + " " + attributes.endurance()
                + Ansi.dim("  (здоровье за каждый уровень)"));
        println();
    }

    public CharacterClass chooseClass() {
        println(Ansi.yellow("Выберите класс персонажа:"));
        CharacterClass[] classes = CharacterClass.values();
        for (int i = 0; i < classes.length; i++) {
            CharacterClass c = classes[i];
            println("  " + Ansi.bold((i + 1) + ") " + c.displayName())
                    + " — здоровье/уровень " + c.healthPerLevel()
                    + ", оружие «" + c.startingWeapon().name() + "»");
            println(Ansi.dim("       стартовый бонус: " + c.firstLevelStat().displayName()
                    + " +1; способности: " + c.abilitiesSummary()));
        }
        int choice = promptInt(1, classes.length, "Ваш выбор");
        println();
        return classes[choice - 1];
    }

    public void showCharacterSheet(GameCharacter hero) {
        println(Ansi.bold(Ansi.green("┌─ Ваш герой ───────────────────────────────────")));
        println("  Класс:        " + Ansi.bold(hero.classDescription())
                + Ansi.dim("  (всего уровней: " + hero.totalLevel() + "/3)"));
        Attributes a = hero.attributes();
        println("  Атрибуты:     Сила " + a.strength()
                + ", Ловкость " + a.agility()
                + ", Выносливость " + a.endurance());
        println("  Здоровье:     " + healthBar(hero.currentHealth(), hero.maxHealth()));
        println("  Оружие:       «" + hero.weapon().name() + "» — урон "
                + hero.weapon().damage() + " (" + hero.weapon().type().displayName().toLowerCase() + ")");
        println("  Урон в атаке: " + Ansi.bold(String.valueOf(hero.weapon().damage() + hero.strength()))
                + Ansi.dim("  (оружие " + hero.weapon().damage() + " + сила " + hero.strength() + ")"));
        if (hero.abilities().isEmpty()) {
            println("  Способности:  " + Ansi.dim("нет"));
        } else {
            println("  Способности:");
            hero.abilities().forEach(ability ->
                    println("     • " + Ansi.bold(ability.name()) + Ansi.dim(" — " + ability.description())));
        }
        println(Ansi.bold(Ansi.green("└───────────────────────────────────────────────")));
        println();
    }

    public void announceMonster(Monster monster, int battleNumber, int winsNeeded) {
        println(Ansi.magenta("─────────────────────────────────────────────────"));
        println(Ansi.bold(Ansi.magenta("Бой " + battleNumber + " из " + winsNeeded
                + ". Ваш противник — " + monster.name() + "!")));
        StringJoiner stats = new StringJoiner(", ");
        stats.add("здоровье " + monster.maxHealth());
        stats.add("сила " + monster.strength());
        stats.add("ловкость " + monster.agility());
        stats.add("выносливость " + monster.endurance());
        println(Ansi.dim("  " + stats));
        println(Ansi.dim("  оружие «" + monster.weapon().name() + "» (урон " + monster.weapon().damage() + ")"
                + ", добыча: «" + monster.reward().name() + "»"));
        if (!monster.abilities().isEmpty()) {
            monster.abilities().forEach(ability ->
                    println(Ansi.dim("  способность: " + ability.name() + " — " + ability.description())));
        }
        println();
    }

    @Override
    public void onBattleStart(Combatant player, Combatant monster, Combatant firstAttacker) {
        println(Ansi.dim("Первым ходит " + firstAttacker.name()
                + " (выше ловкость, при равенстве — герой)."));
        pause("Нажмите Enter, чтобы начать бой...");
    }

    @Override
    public void onAttack(AttackEvent event) {
        String header = "  Ход " + event.turn() + " │ "
                + Ansi.bold(event.attacker().name()) + " ⚔ " + event.defender().name();
        if (!event.hit()) {
            println(header + " — " + Ansi.yellow("промах!")
                    + Ansi.dim(" (бросок " + event.roll() + " из " + event.agilitySum()
                    + " ≤ ловкости цели " + event.defender().agility() + ")"));
        } else {
            println(header);
            println("        " + formatBreakdown(event) + " ⇒ "
                    + Ansi.bold(Ansi.red(event.finalDamage() + " урона")));
            println("        " + event.defender().name() + ": "
                    + healthBar(event.defenderHealthAfter(), event.defenderMaxHealth()));
        }
        sleep(config.turnDelayMillis());
    }

    @Override
    public void onBattleEnd(Combatant winner) {
        println();
        println(Ansi.bold(Ansi.green("★ Победитель боя: " + winner.name() + "!")));
        println();
    }

    private String formatBreakdown(AttackEvent event) {
        StringJoiner parts = new StringJoiner(" + ");
        for (DamageComponent component : event.components()) {
            parts.add(component.source() + " " + component.amount());
        }
        StringBuilder line = new StringBuilder(parts.toString());
        if (event.multiplier() != 1.0) {
            line.append(Ansi.magenta(" ×" + trimMultiplier(event.multiplier()) + " (уязвимость)"));
        }
        if (event.flatReduction() > 0) {
            line.append(Ansi.blue(" −" + event.flatReduction() + " (защита)"));
        }
        return line.toString();
    }

    public boolean askWeaponSwap(Weapon current, Weapon dropped) {
        println(Ansi.yellow("Из противника выпало оружие!"));
        println("  Текущее: «" + current.name() + "» — урон " + current.damage()
                + " (" + current.type().displayName().toLowerCase() + ")");
        println("  Добыча:  «" + dropped.name() + "» — урон " + dropped.damage()
                + " (" + dropped.type().displayName().toLowerCase() + ")");
        boolean swap = askYesNo("Заменить оружие на добычу?");
        println();
        return swap;
    }

    public CharacterClass chooseLevelUpClass(GameCharacter hero) {
        println(Ansi.yellow("Повышение уровня! Текущая сборка: " + Ansi.bold(hero.classDescription())
                + " (всего " + hero.totalLevel() + "/3)."));
        println("Выберите класс для следующего уровня (мультикласс разрешён):");
        CharacterClass[] classes = CharacterClass.values();
        for (int i = 0; i < classes.length; i++) {
            CharacterClass c = classes[i];
            int nextLevel = hero.levelIn(c) + 1;
            println("  " + Ansi.bold((i + 1) + ") " + c.displayName())
                    + " (станет ур. " + nextLevel + ") — получите: " + describeGain(hero, c, nextLevel));
        }
        int choice = promptInt(1, classes.length, "Ваш выбор");
        println();
        return classes[choice - 1];
    }

    private String describeGain(GameCharacter hero, CharacterClass c, int nextLevel) {
        StringJoiner gains = new StringJoiner(", ");
        int projectedEndurance = hero.endurance()
                + (c.statBonusAt(nextLevel) == AttributeType.ENDURANCE ? 1 : 0);
        gains.add("+" + (c.healthPerLevel() + projectedEndurance) + " здоровья");
        if (c.statBonusAt(nextLevel) != null) {
            gains.add(c.statBonusAt(nextLevel).displayName() + " +1");
        }
        c.abilitiesGainedAt(nextLevel, hero.abilityMode())
                .forEach(ability -> gains.add("способность «" + ability.name() + "»"));
        return gains.toString();
    }

    public void showStreak(int wins, int needed) {
        println(Ansi.green("Побед подряд: " + wins + " / " + needed));
        println();
    }

    public void showVictory(GameCharacter hero, int wins) {
        println(Ansi.bold(Ansi.green("╔══════════════════════════════════════════════════╗")));
        println(Ansi.bold(Ansi.green("║   И Г Р А   П Р О Й Д Е Н А !   " + wins + " побед подряд!     ║")));
        println(Ansi.bold(Ansi.green("╚══════════════════════════════════════════════════╝")));
        println("  Ваш легендарный герой: " + Ansi.bold(hero.classDescription()));
        println();
    }

    public void showDefeat(GameCharacter hero, Monster monster, int wins) {
        println(Ansi.bold(Ansi.red("╔══════════════════════════════════════════════════╗")));
        println(Ansi.bold(Ansi.red("║              П О Р А Ж Е Н И Е                    ║")));
        println(Ansi.bold(Ansi.red("╚══════════════════════════════════════════════════╝")));
        println("  " + hero.name() + " (" + hero.classDescription() + ") пал в бою с противником "
                + monster.name() + ".");
        println("  Побед до поражения: " + Ansi.bold(String.valueOf(wins)) + ".");
        println();
    }

    public void goodbye() {
        println(Ansi.cyan("Спасибо за игру! До новых сражений."));
    }

    public boolean askYesNo(String prompt) {
        while (true) {
            print(prompt + " " + Ansi.dim("(д/н): "));
            String line = scanner.hasNextLine() ? scanner.nextLine().trim().toLowerCase() : "н";
            if (line.equals("д") || line.equals("да") || line.equals("y") || line.equals("yes")) {
                return true;
            }
            if (line.equals("н") || line.equals("нет") || line.equals("n") || line.equals("no")) {
                return false;
            }
            println(Ansi.red("  Введите «д» или «н»."));
        }
    }

    public int promptInt(int min, int max, String prompt) {
        while (true) {
            print(prompt + " " + Ansi.dim("[" + min + "–" + max + "]: "));
            if (!scanner.hasNextLine()) {
                return min;
            }
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            println(Ansi.red("  Введите число от " + min + " до " + max + "."));
        }
    }

    public void pause(String prompt) {
        print(Ansi.dim(prompt));
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private String healthBar(int current, int max) {
        int safeMax = Math.max(max, 1);
        int filled = (int) Math.round((double) current / safeMax * BAR_WIDTH);
        filled = Math.max(0, Math.min(BAR_WIDTH, filled));
        String bar = "█".repeat(filled) + "░".repeat(BAR_WIDTH - filled);
        String coloured;
        double fraction = (double) current / safeMax;
        if (fraction > 0.5) {
            coloured = Ansi.green(bar);
        } else if (fraction > 0.25) {
            coloured = Ansi.yellow(bar);
        } else {
            coloured = Ansi.red(bar);
        }
        return "[" + coloured + "] " + current + "/" + max;
    }

    private String trimMultiplier(double multiplier) {
        if (multiplier == Math.rint(multiplier)) {
            return String.valueOf((int) multiplier);
        }
        return String.valueOf(multiplier);
    }

    private void print(String text) {
        System.out.print(text);
    }

    private void println(String text) {
        System.out.println(text);
    }

    private void println() {
        System.out.println();
    }

    private void sleep(long millis) {
        if (millis <= 0) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
