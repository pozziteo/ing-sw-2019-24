package adrenaline.view.cli;

import adrenaline.model.deck.PowerUp;
import adrenaline.model.deck.PowerUpType;
import adrenaline.model.player.Player;
import org.fusesource.jansi.AnsiConsole;

import java.util.List;

/**
 * Class to display messages on command line
 */

//TODO javadoc

public class CliPrinter {

    /**
     * List of colors for ANSI
     */

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * List of background colors for ANSI
     */

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public CliPrinter() {
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
    }

    public void print(String input) {
        AnsiConsole.out.println (input);
    }

    synchronized void printTitle() {
        print (ANSI_CYAN + "WELCOME TO \n" + ANSI_RESET);
        print(ANSI_GREEN +
                "  \\   \\   \\   \\   \\   |   |   |   |   |   /   /   /   /   /   /\n" +
                "   \\   \\   \\   \\   \\  |   |   |   |   |  /   /   /   /   /   / " + ANSI_RESET);

        print(ANSI_BLUE +
                "      _   ____   ____  ____ _    _   _   _     _ _    _ ____\n" +
                "    /   \\|    \\ |  _ \\|  __|  \\ | |/   \\| |   | |  \\ | |  __|\n" +
                "   |  _  |  _  \\| |_) | |__|   \\| |  _  | |   | |   \\| | |__\n" +
                "   | |_| | |_)  |  _ <|  __| |\\   | |_| | |   | | |\\   |  __|\n" +
                "   |  _  |     /| | | | |__| | \\  |  _  | |___| | | \\  | |__\n" +
                "   |_| |_|____/ |_| |_|____|_|  \\_|_| |_|_____|_|_|  \\_|____|\n" + ANSI_RESET);
        print(ANSI_PURPLE +
                "   /   /   /   /   /  |   |   |   |   |  \\   \\   \\   \\   \\   \\\n" +
                "  /   /   /   /   /   |   |   |   |   |   \\   \\   \\   \\   \\   \\\n" + ANSI_RESET);
        print("A SHOOTING GAME IMPLEMENTED BY " + ANSI_RESET + ANSI_RED + "Luca Pome', " + ANSI_RESET + ANSI_YELLOW + "Matteo Pozzi, " + ANSI_RESET + ANSI_GREEN + "Sara Sacco." + ANSI_RESET);
        print("\nPress Enter to continue");
    }

    synchronized void clearScreen() {
        for(int i = 0; i < 30; i++) {
            print("");
        }
    }

    synchronized void printInvalidInput() {
        print("This input is not valid\n");
    }

    synchronized void printConnectionOptions() {
        print("Choose your connection:");
        print("0 - RMI");
        print("1 - Socket");
    }

    synchronized void printMapOptions() {
        print("Which arena will be your battlefield?");
        print("0 - Small arena");
        print("1 - Medium arena (v1)");
        print("2 - Medium arena (v2)");
        print("3 - Large arena");
    }

    synchronized void printInitialSpawnPointOptions(List<PowerUp> powerUps) {
        PowerUpType powerUp1 = powerUps.get(0).getType ();
        PowerUpType powerUp2 = powerUps.get(1).getType ();
        print("Pick your spawn point by discarding the power up card with the corresponding color:");
        print("0 - " + powerUp1.getDescription () + " (spawn in " + powerUp1.getColor ().toLowerCase () + " room)");
        print("1 - " + powerUp2.getDescription () + " (spawn in " + powerUp2.getColor ().toLowerCase () + " room)");
    }

    synchronized void printActionOptions() {
        print("What will you do next?");
        print("0 - Move");
        print("1 - Move and grab");
        print("2 - Shoot an opponent");
        print("3 - Pass this turn");
    }

    synchronized void printRanking(List<Player> ranking) {
        print("Ranking:");
        for (int i = 0; i < ranking.size(); i++) {
            print ((i+1) + " - " + ranking.get(i).getPlayerColor () + " player");
        }
    }

    synchronized void printSetUpComplete() {
        print("You're all set up! The match will now begin");
    }

    synchronized void printNickname() {
        print("Enter your nickname: ");
    }

}
