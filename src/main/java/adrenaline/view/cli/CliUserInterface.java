package adrenaline.view.cli;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.EffectDetails;
import adrenaline.data.data_for_client.responses_for_view.fake_model.*;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.data.data_for_server.data_for_game.*;
import adrenaline.data.data_for_server.data_for_network.AccountSetUp;
import adrenaline.data.data_for_server.requests_for_model.*;
import adrenaline.network.ClientInterface;
import adrenaline.network.rmi.client.RmiClient;
import adrenaline.network.socket.client.SocketClient;
import adrenaline.utils.ConfigFileReader;
import adrenaline.view.UserInterface;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that implements the user interface using command line.
 */

public class CliUserInterface implements UserInterface {
    private static CliUserInterface instance;
    private CliPrinter printer;
    private CliParser parser;
    private ClientInterface client;
    private String nickname;

    private final Object obj = new Object();

    //attributes that represent the file names for each map
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";
    private static final String MEDIUM_1 = PATH + File.separatorChar + "mediummap_1.json";
    private static final String MEDIUM_2 = PATH + File.separatorChar + "mediummap_2.json";
    private static final String LARGE = PATH + File.separatorChar + "largemap.json";

    public CliUserInterface() {
        this.printer = new CliPrinter ();
        this.parser = new CliParser (true);
        this.nickname = null;
    }

    /**
     * Getter to create and obtain the instance of this singleton class.
     * It calls the method that establishes connection with the server.
     * @return istance
     */

    public static CliUserInterface getCliInstance() {
        if (instance == null) {
            instance = new CliUserInterface ();
            instance.establishConnection ();
        }
        return instance;
    }

    /**
     * Method to connect this client to the server based on the connection type chosen.
     */

    private void establishConnection() {
        launchTitleScreen ();
        this.printer.printConnectionOptions ();
        if (this.parser.parseInt (1) == 0) {
            try {
                this.client = new RmiClient(this);
            } catch (RemoteException exc) {
                exc.printStackTrace();
            }
        } else {
            this.client = new SocketClient ("localhost", ConfigFileReader.readConfigFile("socketPort"), this);
        }
        client.connectToServer ();
    }

    /**
     * Shows title screen on command line.
     */

    private void launchTitleScreen() {
        this.printer.printTitle ();
        this.parser.parseEnter ();
    }

    /**
     * Implements UserInterface method. It sends parsed data to the controller.
     * @param data to send
     */

    public void sendToServer(DataForServer data) {
        client.sendData (data);
    }

    /**
     * Implements UserInterface method. It updates data received from server by creating
     * a new thread for async tasks.
     * @param data that has to be updated
     */
    public void updateView(DataForClient data) {
        synchronized (obj) {
            Runnable thread = () ->
                data.updateView(this);
            Thread receiverThread = new Thread(thread);
            receiverThread.start();
        }
    }

    public CliPrinter getPrinter() {
        return this.printer;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUpAccount() {
        printer.printNickname ( );
        String newNickname = this.parser.parseNickname ( );
        AccountSetUp accountData = new AccountSetUp (nickname, newNickname);
        sendToServer (accountData);
    }

    public void notifyTimeOut() {
        printer.print("Time is up. You took too long to make a choice.\n");
    }

    /**
     * This Method asks the player which map he wants to play with
     */
    public void selectMap(String firstPlayerNick){
        parser.setActive (true);
        if (nickname.equals(firstPlayerNick)){
            boolean valid = false;
            while(!valid) {
                this.printer.printMapOptions ( );
                int parsed = this.parser.asyncParseInt (3);
                if (parsed != -1) {
                    if (parsed == 0) {
                        valid = true;
                        ChosenMapSetUp mapData = new ChosenMapSetUp (nickname, SMALL);
                        sendToServer (mapData);
                    } else if (parsed == 1) {
                        valid = true;
                        ChosenMapSetUp mapData = new ChosenMapSetUp (nickname, MEDIUM_1);
                        sendToServer (mapData);
                    } else if (parsed == 2) {
                        valid = true;
                        ChosenMapSetUp mapData = new ChosenMapSetUp (nickname, MEDIUM_2);
                        sendToServer (mapData);
                    } else if (parsed == 3) {
                        valid = true;
                        ChosenMapSetUp mapData = new ChosenMapSetUp (nickname, LARGE);
                        sendToServer (mapData);
                    } else this.printer.printInvalidInput ( );
                }
            }
        } else {
            printer.print ("The first player in your lobby is choosing the arena. Please wait...\n");
        }
    }

    public void chooseSpawnPoint(List<PowerUpDetails> powerUps) {
        printer.printInitialSpawnPointOptions (powerUps);
        int n = parser.parseInt (1);
        ChosenSpawnPointSetUp data = new ChosenSpawnPointSetUp (nickname, powerUps.get (n).getColor ( ));
        sendToServer (data);
        printer.print("Your choice has been sent. Waiting for the other players...\n");
    }


    /**
     * This method asks the player the action he wants to perform
     */

    private void selectAction(){
        parser.setActive (true);
        DataForServer request;
        boolean valid = false;
        while(!valid) {
            this.printer.printActionOptions ( );
            int parsed = this.parser.asyncParseInt (9);
            if (parsed != -1) {
                switch (parsed) {
                    case 0:
                        valid = true;
                        sendAction ("move");
                        break;
                    case 1:
                        valid = true;
                        sendAction ("move and grab");
                        break;
                    case 2:
                        valid = true;
                        sendAction ("shoot");
                        break;
                    case 3:
                        valid = true;
                        sendAction ("power up");
                        break;
                    case 4:
                        valid = true;
                        sendAction ("pass");
                        break;
                    case 5:
                        request = new MapRequest (nickname);
                        sendToServer (request);
                        try {
                            Thread.currentThread ( ).sleep ((long) ConfigFileReader.readConfigFile("cliThread"));
                        } catch (InterruptedException e) {
                            Thread.currentThread ().interrupt ();
                        }
                        break;
                    case 6:
                        request = new SquareDetailsRequest (nickname);
                        sendToServer (request);
                        try {
                            Thread.currentThread ( ).sleep ((long) ConfigFileReader.readConfigFile("cliThread"));
                        } catch (InterruptedException e) {
                            Thread.currentThread ().interrupt ();
                        }
                        break;
                    case 7:
                        request = new MyBoardRequest (nickname);
                        sendToServer (request);
                        break;
                    case 8:
                        request = new BoardsRequest (nickname);
                        sendToServer (request);
                        try {
                            Thread.currentThread ( ).sleep ((long) ConfigFileReader.readConfigFile("cliThread"));
                        } catch (InterruptedException e) {
                            Thread.currentThread ().interrupt ();
                        }
                        break;
                    case 9:
                        request = new RankingRequest (nickname);
                        sendToServer (request);
                        try {
                            Thread.currentThread ( ).sleep ((long) ConfigFileReader.readConfigFile("cliThread"));
                        } catch (InterruptedException e) {
                            Thread.currentThread ().interrupt ();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void sendAction(String actionType) {
        ActionBuilder action = new ActionBuilder(nickname, actionType);
        sendToServer(action);
    }

    private void waitTurn(String nickname) {
        printer.printWaitTurn(nickname);
    }

    public void showTurn(String nickname) {
        parser.setActive(true);
        if (nickname.equals(this.nickname)) {
            selectAction();
        } else {
            waitTurn(nickname);
        }
    }

    public void printMap(String mapName) {
        switch (mapName) {
            case "small map":
                printer.printSmallMap();
                break;
            case "medium map (v1)":
                printer.printMedium1Map();
                break;
            case "medium map (v2)":
                printer.printMedium2Map();
                break;
            case "large map":
                printer.printLargeMap();
                break;
            default:
                break;
        }
    }

    public void printSquareDetails(List<SquareDetails> map) {
        for (int i = 0; i < map.size(); i++)
            printer.printSquareDetails(map.get(i));
    }

    public void printRanking(List<String> ranking) {
        printer.printRanking (ranking);
    }

    public void printAllBoards(List<BoardDetails> boards) {
        for (BoardDetails board : boards)
            printBoard (board);
    }

    public void printBoard(BoardDetails board) {
        printer.printBoard(board);
    }

    public void showPaths(List<Integer> paths) {
        printer.printPaths (paths);
        boolean valid = false;
        while(!valid) {
            int parsed = this.parser.asyncParseInt (11);
            if (parsed != -1) {
                for (Integer i : paths) {
                    if (parsed == i) {
                        valid = true;
                        NewPosition newPosition = new NewPosition (nickname, parsed);
                        sendToServer (newPosition);
                        break;
                    }
                }
                if (!valid) {
                    this.printer.printInvalidInput ( );
                }
            }
        }
    }

    public void showPathsAndGrabOptions(List<Integer> paths, List<SquareDetails> map) {
        printer.printPaths (paths);
        boolean valid = false;
        while(!valid) {
            int parsed = this.parser.asyncParseInt (11);
            if (parsed != -1) {
                for (Integer i : paths) {
                    if (parsed == i) {
                        SquareDetails s = map.get (i);
                        if (s.isSpawnPoint ()) {
                            chooseWeapon ((SpawnPointDetails) s);
                        } else {
                            NewPosition newPosition = new NewPosition (nickname, i);
                            sendToServer(newPosition);
                        }
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    this.printer.printInvalidInput ( );
                }
            }
        }
    }

    private void chooseWeapon(SpawnPointDetails square) {
        NewPositionAndGrabbed newPositionAndGrabbed;
        this.printer.printWeaponListToChoose (square.getWeaponsOnSquare ());
        int parsed = this.parser.asyncParseInt (3);
        if (parsed != -1) {
            newPositionAndGrabbed = new NewPositionAndGrabbed (nickname, square.getId (), square.getWeaponsOnSquare ()[parsed-1].getName ());
            sendToServer (newPositionAndGrabbed);
        }
    }

    public void chooseWeapon(List<WeaponDetails> weapons) {
        DataForServer weapon;
        printer.print("These are your loaded weapons: ");
        printer.printWeaponList (weapons);
        int parsed = this.parser.asyncParseInt (3);
        if (parsed != -1) {
            weapon = new ChosenWeapon (nickname, weapons.get(parsed-1).getName ());
            sendToServer (weapon);
        }
    }

    public void chooseWeaponEffect(List<EffectDetails> effects) {
        printer.print("Choose the effect you want to perform: ");
        printer.printWeaponEffects(effects);
        int parsed = this.parser.asyncParseInt (effects.size()-1);
        if (parsed != -1) {
            DataForServer response = new ChosenEffect (nickname, parsed);
            sendToServer (response);
        }
    }

    private List<String> printPlayersPositions(List<SquareDetails> map) {
        List<String> players = new ArrayList<> ();
        for (SquareDetails s : map) {
            if (! s.getPlayersOnSquare ().isEmpty ()) {
                for (String nick : s.getPlayersOnSquare ()) {
                    players.add(nick);
                    printer.printPlayerPositions (players.size(), nick, s.getId ( ));
                }
            }
        }
        return players;
    }

    public void chooseSingleTarget(List<SquareDetails> map) {
        List<String> players = printPlayersPositions (map);
        printer.print("Choose your target: \n(press " + (players.size ()+1) + " to finish)");
        int parsed = parser.asyncParseInt (players.size () + 1);
        if (parsed != -1) {
            if (parsed != players.size () + 1) {
                List<String> target = new ArrayList<>();
                target.add(players.get(parsed-1));
                DataForServer response = new ChosenTargets(nickname, target, null);
                sendToServer(response);
            }
        }
    }

    public void chooseMultipleTargets(int maxAmount, List<SquareDetails> map) {
        List<String> players = printPlayersPositions (map);
        printer.print("Choose your targets (please keep in mind that for weapons with multiple effects, these will be applied following the description's order | press " + (players.size ()+1) + " to finish beforehand): ");
        List<String> targets = new LinkedList<> ();
        int amountChosen = 0;
        while (amountChosen < maxAmount) {
            printer.printChooseTargets (maxAmount - amountChosen);
            int parsed = this.parser.asyncParseInt (players.size ()+1);
            if (parsed != -1) {
                if (parsed != players.size ()+1) {
                    amountChosen++;
                    targets.add (players.get (parsed - 1));
                } else
                    break;
            }
        }
        if (amountChosen > 0) {
            DataForServer chosenTargets = new ChosenTargets (nickname, targets, null);
            sendToServer (chosenTargets);
        }
    }

    public void chooseAreaToTarget(int amount, List<SquareDetails> map) {
        printPlayersPositions (map);
        List<Integer> targetSquares = new ArrayList<> ();
        DataForServer response;
        int amountChosen = 0;
        while (amountChosen < amount) {
            printer.print ("Type the id of the square you want to target/the id of a square in the room you want to target (" + (amount-amountChosen) + " left):");
            int parsed = parser.asyncParseInt (map.size ( ));
            if (parsed != -1) {
                amountChosen++;
                targetSquares.add(parsed);
            }
        }
        if (amountChosen > 0) {
            response = new ChosenTargets (nickname, null, targetSquares);
            sendToServer (response);
        }
    }

    public void askReload(List<String> ammo, List<WeaponDetails> weapons) {
        printer.printReload();
        int parsed = this.parser.asyncParseInt (1);
        if (parsed != -1) {
            if (parsed == 0) {
                DataForServer response = new ReloadResponse(nickname, false, "");
                sendToServer (response);
            } else if (parsed == 1) {
                printer.print ("Owned ammo: " + ammo);
                printer.print ("These are your unloaded weapons: ");
                printer.printWeaponList (weapons);
                int parsed1 = this.parser.asyncParseInt (weapons.size ());
                if (parsed1 != -1) {
                    DataForServer response = new ReloadResponse (nickname, true, weapons.get(parsed1-1).getName ());
                    sendToServer (response);
                }
            }
        }

    }

    public void discardWeapon(List<WeaponDetails> weapons) {
        printer.print ("You reached the max amount of weapons you can hold. Please discard one of the following: ");
        printer.printWeaponList (weapons);
        int parsed = this.parser.asyncParseInt (weapons.size ( ));
        if (parsed != -1) {
            DataForServer response = new DiscardedWeapon (nickname, weapons.get (parsed - 1).getName ( ));
            sendToServer (response);
        }
    }

    public void showEndGameScreen(List<String> ranking) {
        printer.printRanking (ranking);
        System.exit (0);
    }
}
