package adrenaline.view.cli;

import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.map.NormalSquare;
import adrenaline.model.map.SpawnPoint;
import adrenaline.model.map.Square;
import adrenaline.model.player.Player;
import org.fusesource.jansi.AnsiConsole;

import java.util.Arrays;
import java.util.List;

/**
 * Class to display messages on command line
 */


public class CliPrinter {

    /**
     * List of colors for ANSI
     */

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001b[30m";
    public static final String ANSI_RED = "\u001b[31;1m";
    public static final String ANSI_GREEN = "\u001b[32;1m";
    public static final String ANSI_YELLOW = "\u001b[33;1m";
    public static final String ANSI_BLUE = "\u001b[34;1m";
    public static final String ANSI_PURPLE = "\u001b[35;1m";
    public static final String ANSI_CYAN = "\u001b[36;1m";
    public static final String ANSI_WHITE = "\u001b[37;1m";

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

    /**
     * Printer Method
     * @param input is what it's printed
     */
    public void print(String input) {
        AnsiConsole.out.println (input);
    }

    /**
     * Method that prints the title of the game
     */
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

    /**
     * Method that clears the screen (scrolls down)
     */
    synchronized void clearScreen() {
        for(int i = 0; i < 30; i++) {
            print("");
        }
    }

    /**
     * Method that reports an invalid input
     */
    synchronized void printInvalidInput() {
        print("This input is not valid.\nTry again:\n");
    }

    /**
     * Method that shows to the client the Connection options
     */
    synchronized void printConnectionOptions() {
        print("Choose your connection:");
        print("0 - RMI");
        print("1 - Socket");
    }

    /**
     * Method that shows to the client the Map Selector
     */
    synchronized void printMapOptions() {
        print("Since you are the first player in your lobby, you get to choose your battlefield:\n");
        print("0 - Small arena");
        print("1 - Medium arena (v1)");
        print("2 - Medium arena (v2)");
        print("3 - Large arena");
    }

    /**
     * This Method declares the player's starting position
     * after he keeps a PowerUp
     * @param powerUps is the couple of powerUps the player gets at the beginning of the game
     */
    synchronized void printInitialSpawnPointOptions(List<PowerUp> powerUps) {
        PowerUp powerUp1 = powerUps.get(0);
        PowerUp powerUp2 = powerUps.get(1);
        print("Pick your spawn point by discarding the power up card with the corresponding color:");
        print("0 - " + powerUp1.getType().getDescription() + " (spawn in " + powerUp1.getAmmo().getColor().toLowerCase () + " room)");
        print("1 - " + powerUp2.getType().getDescription() + " (spawn in " + powerUp2.getAmmo().getColor().toLowerCase()+ " room)");
    }

    /**
     * Method that shows to the player the legal actions he can perform
     */
    synchronized void printActionOptions() {
        print("What will you do next?");
        print("0 - Move");
        print("1 - Move and grab");
        print("2 - Shoot an opponent");
        print("3 - Use PowerUp");
        print("4 - Pass this turn");
    }

    /**
     * Method that prints the ranking
     * @param ranking is a sorted list
     */
    synchronized void printRanking(List<Player> ranking) {
        print("Current ranking:");
        for (int i = 0; i < ranking.size(); i++) {
            print ((i+1) + " - " + ranking.get(i).getPlayerColor () + " player");
        }
    }

    /**
     * Method that tells when the game is ready to start
     */
    synchronized void printSetUpComplete() {
        print("You're all set up! The match will now begin");
    }

    /**
     * Method that asks the player for his nickname
     */
    synchronized void printNickname() {
        print("Enter your nickname: ");
    }

    synchronized void printSmallMap() {
        //first row
        print(ANSI_BLUE + " ________"   +"  ________ " + " ________" + ANSI_RESET + "          ");
        print(ANSI_BLUE + "/        \\" +"/        \\/" +"        \\ " + ANSI_RESET + "           ");
        print(ANSI_BLUE + "|         "  +"          " + "         |"+ ANSI_RESET + "          ");
        print(ANSI_BLUE + "|    0    "  +"    1     " + "    2 ╬  |" + ANSI_RESET + "          ");
        print(ANSI_BLUE +  "|         "  +"          " + "         |" + ANSI_RESET + "          ");
        print(ANSI_BLUE + "\\__    __/"  +"\\________/\\" + "__    __/" + ANSI_RESET + "          ");
        //second row
        print(ANSI_RED + " __"+ANSI_RESET+ANSI_BLUE+"║"+ANSI_RESET+ ANSI_RED+"  ║__ "  +" ________ " + " __"+ANSI_RESET+ANSI_BLUE+"║"+ANSI_RESET+ ANSI_RED+"  ║__ " + ANSI_RESET + ANSI_YELLOW+ "  ________ " + ANSI_RESET);
        print(ANSI_RED + "/        \\" + "/        \\"+  "/        \\" + ANSI_RESET + ANSI_YELLOW + " /        \\" + ANSI_RESET);
        print(ANSI_RED + "|          "  +"         " + "          ╩" + ANSI_RESET + ANSI_YELLOW + "         |" + ANSI_RESET);
        print(ANSI_RED + "| ╬  4    "  +"    5     " + "     6     " + ANSI_RESET + ANSI_YELLOW + "     7   |" + ANSI_RESET);
        print(ANSI_RED + "|         "  +"         " + "           " + ANSI_RESET + ANSI_YELLOW + "╦         |" + ANSI_RESET);
        print(ANSI_RED + "\\________/" +"\\__    __/" +"\\________/" + ANSI_RESET + ANSI_YELLOW + " \\        |" + ANSI_RESET);
        //third row
        print("          " + ANSI_WHITE + " __"+ANSI_RESET+ANSI_RED+"║"+ANSI_RESET+ ANSI_WHITE+"  ║__ " +" ________" + ANSI_RESET + ANSI_YELLOW + "  |        |" + ANSI_RESET);
        print("          " + ANSI_WHITE + "/        \\"+ "/        \\" + ANSI_RESET + ANSI_YELLOW + " /        |" + ANSI_RESET);
        print("          " + ANSI_WHITE + "|        " + "           ╩" + ANSI_RESET + ANSI_YELLOW + "         |" + ANSI_RESET);
        print("          " + ANSI_WHITE + "|   9     " + "    10   " + ANSI_RESET + ANSI_YELLOW + "      11 ╬ |" + ANSI_RESET);
        print("          " + ANSI_WHITE + "|         " + "          " + ANSI_RESET + ANSI_YELLOW + "╦         |" + ANSI_RESET);
        print("          " + ANSI_WHITE + "\\________/\\" + "________/ " + ANSI_RESET + ANSI_YELLOW + "\\________/" + ANSI_RESET);
    }

    synchronized void printMedium1Map() {
        //first row
        print(ANSI_RED + " _______ " + ANSI_RESET + ANSI_BLUE+"  ________" +  "  ________" + ANSI_RESET +"          ");
        print(ANSI_RED + "/       \\ " + ANSI_RESET +ANSI_BLUE+"/        \\" + "/        \\ " + ANSI_RESET +"         ");
        print(ANSI_RED + "|        ╩" + ANSI_RESET +ANSI_BLUE+"          " + "         |" + ANSI_RESET + "         ");
        print(ANSI_RED + "|    0    " + ANSI_RESET +ANSI_BLUE+"    1     " + "   2 ╬   |" + ANSI_RESET + "         ");
        print(ANSI_RED + "|        " + ANSI_RESET +ANSI_BLUE+"╦           " +  "        |" + ANSI_RESET + "          ");
        print(ANSI_RED + "|       / " +ANSI_RESET +ANSI_BLUE+ "\\__    __/" + "\\__    __/" + ANSI_RESET +"          ");
        //second row
        print(ANSI_RED + "|       | " +ANSI_RESET + ANSI_PURPLE +" __" +ANSI_RESET+ANSI_BLUE+"║"+ANSI_RESET +ANSI_PURPLE +"  ║__ " +ANSI_RESET +ANSI_PURPLE +  " __"+ANSI_RESET+ANSI_BLUE+"║"+ANSI_RESET +ANSI_PURPLE + "  ║__ " + ANSI_RESET + ANSI_YELLOW+ "  ________ " + ANSI_RESET);
        print(ANSI_RED + "|       \\ " +ANSI_RESET + ANSI_PURPLE +"/        \\" + "/        \\"+ ANSI_RESET + ANSI_YELLOW + " /        \\" + ANSI_RESET);
        print(ANSI_RED + "|        |" +ANSI_RESET + ANSI_PURPLE +"|         " +"          ╩" + ANSI_RESET + ANSI_YELLOW + "         |" + ANSI_RESET);
        print(ANSI_RED + "| ╬  4   |" +ANSI_RESET + ANSI_PURPLE +"|   5     " + "    6      " + ANSI_RESET + ANSI_YELLOW + "    7    |" + ANSI_RESET);
        print(ANSI_RED + "|        |" +ANSI_RESET + ANSI_PURPLE +"|         " +"          " + ANSI_RESET + ANSI_YELLOW + "╦         |" + ANSI_RESET);
        print(ANSI_RED + "\\__    __/" +ANSI_RESET + ANSI_PURPLE +"\\__    __/"+"\\________/" + ANSI_RESET + ANSI_YELLOW + " \\        |" + ANSI_RESET);
//third row
        print(ANSI_WHITE + " __"+ANSI_RESET+ANSI_RED+"║"+ANSI_RESET+ ANSI_WHITE+"  ║__ " + " __"+ANSI_RESET+ANSI_PURPLE+"║"+ANSI_RESET+ ANSI_WHITE+"  ║__" +"  ________ " + ANSI_RESET + ANSI_YELLOW + " |        |" + ANSI_RESET);
        print(ANSI_WHITE + "/        \\" + "/        \\" + "/        \\" + ANSI_RESET + ANSI_YELLOW + " /        |" + ANSI_RESET);
        print(ANSI_WHITE + "|        " + "           " + "          ╩" + ANSI_RESET + ANSI_YELLOW + "         |" + ANSI_RESET);
        print(ANSI_WHITE + "|    8   " + "     9      " + "   10    " + ANSI_RESET + ANSI_YELLOW + "     11 ╬ |" + ANSI_RESET);
        print(ANSI_WHITE + "|        " + "            " + "         " + ANSI_RESET + ANSI_YELLOW + "╦         |" + ANSI_RESET);
        print(ANSI_WHITE + "\\________/" + "\\________/" + "\\________/ " + ANSI_RESET + ANSI_YELLOW + "\\________/" + ANSI_RESET);


    }

    synchronized void printMedium2Map() {
//first row
        print(ANSI_BLUE + " ________"   +"  _______ " + " _________" + ANSI_RESET + ANSI_GREEN +"   _______");
        print(ANSI_BLUE + "/        \\" +"/       \\/" +"         \\ " + ANSI_RESET +ANSI_GREEN+"/       \\");
        print(ANSI_BLUE + "|         "  +"          " + "          ╩" + ANSI_RESET + ANSI_GREEN +"        |");
        print(ANSI_BLUE + "|    0    "  +"    1     " + "   2 ╬     " + ANSI_RESET + ANSI_GREEN +"   3    |");
        print(ANSI_BLUE + "|         "  +"          " + "          " + ANSI_RESET + ANSI_GREEN +"╦        |");
        print(ANSI_BLUE + "\\__    __/"  +"\\_______/\\" + "___    __/" + ANSI_RESET + ANSI_GREEN +" \\_    __/");
        //second row
        print(ANSI_RED + " __"+ANSI_RESET+ANSI_BLUE+"║"+ANSI_RESET+ ANSI_RED+"  ║__ "  +" ________ " +ANSI_RESET +ANSI_YELLOW + " __"+ANSI_RESET+ANSI_BLUE+"║"+ANSI_RESET +ANSI_YELLOW + "  ║__ " + " __"+ANSI_RESET+ANSI_GREEN+"║"+ANSI_RESET +ANSI_YELLOW +"  ║__ " + ANSI_RESET);
        print(ANSI_RED + "/        \\"  +"/        \\" +ANSI_RESET +ANSI_YELLOW + "/        \\"+ "/        \\" + ANSI_RESET);
        print(ANSI_RED + "|          "  +"        |" + ANSI_RESET +ANSI_YELLOW +"|          " + "        |" + ANSI_RESET);
        print(ANSI_RED + "|  ╬ 4    "  +"    5    |" + ANSI_RESET +ANSI_YELLOW +"|   6      " + "   7    |" + ANSI_RESET);
        print(ANSI_RED + "|          "  +"        |" + ANSI_RESET +ANSI_YELLOW +"|          " + "        |" + ANSI_RESET);
        print(ANSI_RED + "\\________/" +"\\__    __/" +ANSI_RESET +ANSI_YELLOW +" \\       /"+  "\\        |" + ANSI_RESET);
        //third row
        print("          " + ANSI_WHITE + " __"+ANSI_RESET+ANSI_RED+"║"+ANSI_RESET+ ANSI_WHITE+"  ║_ " +  ANSI_RESET + ANSI_YELLOW + "  /       \\" +"/        |" + ANSI_RESET);
        print("          " + ANSI_WHITE + "/       \\" +ANSI_RESET + ANSI_YELLOW + " |         " +"         |" + ANSI_RESET);
        print("          " + ANSI_WHITE + "|        " + ANSI_RESET + ANSI_YELLOW + "╩           " +"        |" + ANSI_RESET);
        print("          " + ANSI_WHITE + "|   9     " +  ANSI_RESET + ANSI_YELLOW + "    10     " +"   11 ╬ |" + ANSI_RESET);
        print("          " + ANSI_WHITE + "|        ╦" +  ANSI_RESET + ANSI_YELLOW + "           " +"        |" + ANSI_RESET);
        print("          " + ANSI_WHITE + "\\_______/ " +  ANSI_RESET + ANSI_YELLOW + "\\________/" +"\\________/" + ANSI_RESET);

    }

    synchronized void printLargeMap() {
        //first row
        print(ANSI_RED + " _______ " + ANSI_RESET + ANSI_BLUE+"  ________" +  "  ________" + ANSI_RESET + ANSI_GREEN +"   _______");
        print(ANSI_RED + "/       \\ " + ANSI_RESET +ANSI_BLUE+"/        \\" + "/        \\ " + ANSI_RESET +ANSI_GREEN+"/       \\");
        print(ANSI_RED + "|        ╩" + ANSI_RESET +ANSI_BLUE+"          " + "          ╩" + ANSI_RESET + ANSI_GREEN +"        |");
        print(ANSI_RED + "|    0    " + ANSI_RESET +ANSI_BLUE+"    1     " + "   2 ╬     " + ANSI_RESET + ANSI_GREEN +"   3    |");
        print(ANSI_RED + "|        " + ANSI_RESET +ANSI_BLUE+"╦           " +  "         " + ANSI_RESET + ANSI_GREEN +"╦        |");
        print(ANSI_RED + "|       / " +ANSI_RESET +ANSI_BLUE+ "\\__    __/" + "\\__    __/" + ANSI_RESET + ANSI_GREEN +" \\_    __/");
        //second row
        print(ANSI_RED + "|       | " +ANSI_RESET + ANSI_PURPLE +" __" +ANSI_RESET+ANSI_BLUE+"║"+ANSI_RESET +ANSI_PURPLE +"  ║__ " +ANSI_RESET +ANSI_YELLOW +  " __"+ANSI_RESET+ANSI_BLUE+"║"+ANSI_RESET +ANSI_YELLOW + "  ║__ " + " __"+ANSI_RESET+ANSI_GREEN+"║"+ANSI_RESET +ANSI_YELLOW +"  ║__ " + ANSI_RESET);
        print(ANSI_RED + "|       \\ " +ANSI_RESET + ANSI_PURPLE +"/        \\" +ANSI_RESET +ANSI_YELLOW + "/        \\"+ "/        \\" + ANSI_RESET);
        print(ANSI_RED + "|        |" +ANSI_RESET + ANSI_PURPLE +"|        |" + ANSI_RESET +ANSI_YELLOW +"|          " + "        |" + ANSI_RESET);
        print(ANSI_RED + "|  ╬ 4   |" +ANSI_RESET + ANSI_PURPLE +"|   5    |" + ANSI_RESET +ANSI_YELLOW +"|   6      " + "   7    |" + ANSI_RESET);
        print(ANSI_RED + "|        |" +ANSI_RESET + ANSI_PURPLE +"|        |" + ANSI_RESET +ANSI_YELLOW +"|          " + "        |" + ANSI_RESET);
        print(ANSI_RED + "\\__    __/" +ANSI_RESET + ANSI_PURPLE +"\\__    __/" +ANSI_RESET +ANSI_YELLOW +" \\       /"+  "\\        |" + ANSI_RESET);
        //third row
        print(ANSI_WHITE + " __"+ANSI_RESET+ANSI_RED+"║"+ANSI_RESET+ ANSI_WHITE+"  ║__ " + " __"+ ANSI_RESET+ANSI_PURPLE+"║"+ANSI_RESET+ ANSI_WHITE +"  ║_ " + ANSI_RESET + ANSI_YELLOW + "  /       \\" +"/        |" + ANSI_RESET);
        print(ANSI_WHITE + "/        \\" + "/       \\" + ANSI_RESET + ANSI_YELLOW + " |         " +"         |" + ANSI_RESET);
        print(ANSI_WHITE + "|        " + "          " + ANSI_RESET + ANSI_YELLOW + "╩           " +"        |" + ANSI_RESET);
        print(ANSI_WHITE + "|    8   " + "     9     " + ANSI_RESET + ANSI_YELLOW + "    10     " +"   11 ╬ |" + ANSI_RESET);
        print(ANSI_WHITE + "|        " + "          ╦" + ANSI_RESET + ANSI_YELLOW +  "           " +"        |" + ANSI_RESET);
        print(ANSI_WHITE + "\\________/" + "\\_______/ " + ANSI_RESET + ANSI_YELLOW + "\\________/" +"\\________/" + ANSI_RESET);

    }

    synchronized void printSquareDetails(Square square) {
        print("Square " + square.getSquareId () + ":");
        print("Players on this square: ");
        if (square.getPlayersOnSquare().isEmpty()) {
            print("none");
        } else {
            for (int i = 0; i < square.getPlayersOnSquare().size(); i++) {
                print((i + 1) + ": " + square.getPlayersOnSquare().get(i).getPlayerName());
            }
        }
        if (square.isSpawnPoint ()) {
            print("Weapons you can grab: ");
            for (int i = 0; i < ((SpawnPoint) square).getWeapons ().length; i++) {
                print((i+1) + ": " + ((SpawnPoint) square).getWeapons ()[i].getWeaponsName ());
            }
            print("\n");
        } else {
            print("Tile details: " + ((NormalSquare) square).getPlacedTile ().getTileDescription ().toUpperCase() + "\n");
        }
    }

    synchronized void printWeaponList(List<Weapon> weapons){
        print("These are your weapons: ");
        for (int i = 0; i < weapons.size(); i++){
            print((i+1) + " - " + weapons.get(i).getWeaponsName());
        }
    }

    synchronized void printWaitTurn(String nickname) {
        print(nickname + " is playing. Please wait your turn...\n");
    }
}
