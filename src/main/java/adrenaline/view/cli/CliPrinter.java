package adrenaline.view.cli;

import adrenaline.data.data_for_client.responses_for_view.fake_model.EffectDetails;
import adrenaline.data.data_for_client.responses_for_view.fake_model.*;
import org.fusesource.jansi.AnsiConsole;

import java.util.Collections;
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

    public static final String YES = "1 - Yes";
    public static final String NO = "0 - No";

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
    public synchronized void print(String input) {
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
    synchronized void printSpawnPointOptions(List<PowerUpDetails> powerUps) {
        print("Pick your spawn point by discarding the power up card with the corresponding color:");
        for (int i = 0; i < powerUps.size(); i++) {
            print(i + " - " + powerUps.get(i).getType () + " (spawn in " + powerUps.get(i).getColor ().toLowerCase () + " room)");
        }
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
        print("5 - View the map");
        print("6 - View all the squares' details");
        print("7 - View your player board");
        print("8 - View your and your opponents' player boards");
        print("9 - View the current ranking");
    }

    /**
     * Method that prints the ranking
     * @param ranking is a sorted list
     */
    synchronized void printRanking(List<String> ranking) {
        Collections.reverse(ranking);
        print("Ranking:");
        for (int i = 0; i < ranking.size(); i++) {
            print ((i+1) + " - " + ranking.get(i));
        }
    }

    /**
     * Method that asks the player for his nickname
     */
    synchronized void printNickname() {
        print("Enter your nickname: ");
    }

    /**
     * Method to print the Small Map
     */
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

    /**
     * Method to print the Medium Map v1
     */
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

    /**
     * Method to print the Medium Map v2
     */
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

    /**
     * Method to print the Large Map
     */
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

    /**
     * Method that prints the information about one square
     * @param square is the square you need to know about
     */
    synchronized void printSquareDetails(SquareDetails square) {
        print(ANSI_CYAN + "Square " + square.getId () + ":" + ANSI_RESET);
        print("Players on this square: ");
        if (square.getPlayersOnSquare().isEmpty()) {
            print("none");
        } else {
            for (int i = 0; i < square.getPlayersOnSquare().size(); i++) {
                print((i + 1) + ": " + square.getPlayersOnSquare().get(i));
            }
        }
        if (square.isSpawnPoint ()) {
            print("Weapons you can grab: ");
            for (int i = 0; i < ((SpawnPointDetails) square).getWeaponsOnSquare ().length; i++) {
                print ((i + 1) + ": " + ((SpawnPointDetails) square).getWeaponsOnSquare ()[i].getName ());
            }
        } else {
            print("Tile details: " + ((NormalSquareDetails) square).getTileOnSquare().toUpperCase());
        }
    }

    synchronized  void printPaths(List<Integer> paths) {
        print("Choose the square you want to move to:\n");
        System.out.print("[  ");
        for (Integer i : paths) {
            System.out.print(i.toString () + "  ");
        }
        System.out.print("]\n");
    }

    synchronized void printWeaponListToChoose(WeaponDetails[] weapons){
        print("These are the weapons you can grab from this square: ");
        for (int i = 0; i < weapons.length; i++){
            print (i + " - " + weapons[i].getName () + ": " + weapons[i].getDescription () + "(cost to grab: " + weapons[i].getGrabCost () + ")\n");
        }
    }

    /**
     * Method to print the player's Weapon List
     * @param weapons is the ArrayList of weapons
     */
    synchronized void printWeaponList(List<WeaponDetails> weapons){
        if (weapons.isEmpty ())
            print("No weapons");
        else {
            for (int i = 0; i < weapons.size ( ); i++) {
                print (i + " - " + ANSI_PURPLE + weapons.get (i).getName ( ) + ANSI_RESET + ": " + weapons.get (i).getDescription ( ) + "(cost to reload :" + weapons.get (i).getAmmoCost ( ) + ")\n");
            }
        }
    }

    synchronized void printUseAsAmmo() {
        print("Do you want to use the chosen power up as bonus ammo?");
        print(NO);
        print(YES);
    }

    synchronized void printWeaponEffects(List<EffectDetails> effects) {
        int i = 0;
        for (EffectDetails e : effects) {
            if (e.getEffectType ().equals("base effect")) {
                print(i + " - weapon's base effect (usable always and without spending additional ammo)");
            } else if (e.getEffectType ().equals("optional effect") && e.isAlternativeMode ()) {
                print(i + " - weapon's alternative fire mode (usable only instead of base mode, may cost additional ammo)");
            } else if (e.getEffectType ().equals ("optional effect")) {
                print(i + " - weapon's optional effect (usable only in addition of base mode, may cost additional ammo, usable before base effect: " + e.isUsableBeforeBase () + ")");
            }
            i++;
        }
    }

    /**
     * Method to print the player's PowerUp List
     * @param powerUpDetails is the ArrayList of PowerUp
     */
    synchronized void printPowerUpList(List<PowerUpDetails> powerUpDetails){
        print("These are your power ups (press " + powerUpDetails.size() + " if you don't want to use any):");
        for (int i=0; i < powerUpDetails.size(); i++){
            print(i + " - " + powerUpDetails.get(i).getType () + " - Bonus ammo color: " + powerUpDetails.get(i).getColor ());
        }
    }

    /**
     * Method to print the player's Ammo List
     * @param ammos is the ArrayList of ammos
     */
    synchronized void printAmmoList(List<String> ammos){
        print("These are your ammos: " + ammos);
    }

    /**
     * @param nickname is the player who is performing his actions now
     */
    synchronized void printWaitTurn(String nickname) {
        print(nickname + " is playing. Please wait your turn...\n");
    }

    synchronized void printChooseTargets(int n) {
        print("You can choose up to " + n + " targets:");
    }

    synchronized void printPlayerPositions(int n, String nickname, int squareId) {
        print(n + " - " + nickname + " is on square " + squareId);
    }

    synchronized void printTargetingScope() {
        print("Do you want to use Targeting Scope to give one additional damage to someone?");
        print(NO);
        print(YES);
    }

    synchronized void printBoard(BoardDetails board) {
        String color = "";
        switch (board.getColor ()) {
            case "Red":
                color = ANSI_RED;
                break;
            case "Blue":
                color = ANSI_BLUE;
                break;
            case "Yellow":
                color = ANSI_YELLOW;
                break;
            case "Green":
                color = ANSI_GREEN;
                break;
            case "Grey":
                color = ANSI_WHITE;
                break;
            default:
                break;
        }
        print(color + board.getNickname () + ANSI_RESET + "'s board: ");
        print("- color: " + board.getColor ());
        print("- on square: " + board.getPosition ());
        print("- loaded weapons: " );
        printWeaponList (board.getLoadedWeapons ());
        print("- damage taken: " + board.getDamageTaken ());
        print("- marks received: " + board.getReceivedMarks ());
        print("- points: " + board.getPointsToken());
        print("- unloaded weapons: " );
        printWeaponList (board.getUnloadedWeapons ());
        print("- owned ammo: " + board.getOwnedAmmo ());
        print("\n");
    }

    synchronized void printReload() {
        print("Do you want to reload any of your weapons?\n");
        print(NO);
        print(YES);
    }

    synchronized void printEndGame() {
        print(ANSI_PURPLE + "Thanks for playing!" + ANSI_RESET);
    }
}
