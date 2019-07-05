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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class that implements the user interface using command line.
 */

public class CliUserInterface implements UserInterface {
    private static CliUserInterface instance;
    private CliPrinter printer;
    private CliParser parser;
    private ClientInterface client;
    private String nickname;
    private Map<String, String> playerColors;

    private final Object obj = new Object();

    //attributes that represent the file names for each map
    private static final String PATH = "/maps/";
    private static final String SMALL = PATH + "smallmap.json";
    private static final String MEDIUM_1 = PATH + "mediummap_1.json";
    private static final String MEDIUM_2 = PATH +"mediummap_2.json";
    private static final String LARGE = PATH +"largemap.json";

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
            Runnable thread = () -> {
                Thread.currentThread ().setName ("Cli Receiver Thread");
                data.updateView (this);
            };
            Thread receiverThread = new Thread(thread);
            receiverThread.start();
        }
    }

    /**
     * Getter method to obtain the Cli's printer
     * @return printer
     */

    public CliPrinter getPrinter() {
        return this.printer;
    }

    /**
     * Getter method to obtain the client's nickname
     * @return nickname
     */

    public String getNickname() {
        return this.nickname;
    }

    /**
     * Setter method to set the client's nickname
     * @param nickname of the client using this cli
     */

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Method to choose the nickname
     */

    public void setUpAccount() {
        printer.printNickname ( );
        String newNickname = this.parser.parseNickname ( );
        AccountSetUp accountData = new AccountSetUp (nickname, newNickname);
        sendToServer (accountData);
    }

    /**
     * Method to notify the client that the timer run out
     * @param nickname of the player that made the timer run out
     */

    public void notifyTimeOut(String nickname) {
        parser.setActive (false);
        if (this.nickname.equals(nickname))
            printer.print("Time is up. You took too long to make a choice.\n");
    }

    /**
     * This Method asks the player which map he wants to play with
     */
    public void selectMap(String firstPlayerNick, Map<String, String> colors){
        parser.setActive (true);
        this.playerColors = colors;
        printer.print("Your color is " + playerColors.get(getNickname()));
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

    /**
     * Method to ask the player which room they want to spawn in
     * @param powerUps is the list of power ups where they can choose one to discard
     */

    public void chooseSpawnPoint(List<PowerUpDetails> powerUps) {
        printer.printSpawnPointOptions (powerUps);
        int n = parser.parseInt (powerUps.size()-1);
        ChosenSpawnPointSetUp data = new ChosenSpawnPointSetUp (nickname, powerUps.get (n).getColor ( ));
        sendToServer (data);
        printer.print("Your choice has been sent...\n");
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
                        putThreadToSleep ();
                        break;
                    case 6:
                        request = new SquareDetailsRequest (nickname);
                        sendToServer (request);
                        putThreadToSleep ();
                        break;
                    case 7:
                        request = new MyBoardRequest (nickname);
                        sendToServer (request);
                        putThreadToSleep ();
                        break;
                    case 8:
                        request = new BoardsRequest (nickname);
                        sendToServer (request);
                        putThreadToSleep ();
                        break;
                    case 9:
                        request = new RankingRequest (nickname);
                        sendToServer (request);
                        putThreadToSleep ();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * This method asks the player the action he wants to perform during final frenzy
     */

    private void selectFinalFrenzyAction(){
        parser.setActive (true);
        DataForServer request;
        boolean valid = false;
        while(!valid) {
            this.printer.printFinalFrenzyActionOptions ( );
            int parsed = this.parser.asyncParseInt (8);
            if (parsed != -1) {
                switch (parsed) {
                    case 0:
                        valid = true;
                        sendAction ("move and grab");
                        break;
                    case 1:
                        valid = true;
                        sendAction ("shoot");
                        break;
                    case 2:
                        valid = true;
                        sendAction ("power up");
                        break;
                    case 3:
                        valid = true;
                        sendAction ("pass");
                        break;
                    case 4:
                        request = new MapRequest (nickname);
                        sendToServer (request);
                        putThreadToSleep ();
                        break;
                    case 5:
                        request = new SquareDetailsRequest (nickname);
                        sendToServer (request);
                        putThreadToSleep ();
                        break;
                    case 6:
                        request = new MyBoardRequest (nickname);
                        sendToServer (request);
                        putThreadToSleep ();
                        break;
                    case 7:
                        request = new BoardsRequest (nickname);
                        sendToServer (request);
                        putThreadToSleep ();
                        break;
                    case 8:
                        request = new RankingRequest (nickname);
                        sendToServer (request);
                        putThreadToSleep ();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Method to make current thread sleep for 3 seconds
     */

    private void putThreadToSleep() {
        try {
            Thread.sleep ((long) ConfigFileReader.readConfigFile("cliThread"));
        } catch (InterruptedException e) {
            Thread.currentThread ().interrupt ();
        }
    }

    /**
     * Method to send an action to the server
     * @param actionType is the type of action chosen
     */

    private void sendAction(String actionType) {
        ActionBuilder action = new ActionBuilder(nickname, actionType);
        sendToServer(action);
    }

    /**
     * Method to ask the player to wait their turn
     * @param nickname of this player
     */

    private void waitTurn(String nickname) {
        printer.printWaitTurn(nickname);
    }

    /**
     * Method to show the player that it's their turn and they can make an action
     * @param nickname of player
     */

    public void showTurn(String nickname) {
        parser.setActive(true);
        if (nickname.equals(this.nickname)) {
            selectAction();
        } else {
            waitTurn(nickname);
        }
    }

    /**
     * Method to show the player that it's their turn and they can make a final frenzy action
     * @param nickname of player
     */

    public void showFinalFrenzyTurn(String nickname, boolean beforeFirstPlayer) {
        parser.setActive(true);
        if (nickname.equals(this.nickname)) {
            if (beforeFirstPlayer)
                selectAction ();
            else {
                selectFinalFrenzyAction();
            }
        } else {
            waitTurn(nickname);
        }
    }

    /**
     * Method to print the map
     * @param mapName is the name of the map chosen for the game
     */

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

    /**
     * Method to inform the player of which map is being used for the game
     * @param name of the map
     */

    public void printMapInit(String name) {
        printer.print ("Map has been initialized to " + name);
    }

    /**
     * Method to print the square details of the map
     * @param map is the list of square details
     */

    public void printSquareDetails(List<SquareDetails> map) {
        for (int i = 0; i < map.size(); i++)
            printer.printSquareDetails(map.get(i));
    }

    /**
     * Method to print the ranking
     * @param ranking is the list of players in order by points
     */

    public void printRanking(List<String> ranking) {
        printer.printRanking (ranking);
    }

    /**
     * Method to print all the boards' details
     * @param boards to print
     */

    public void printAllBoards(List<BoardDetails> boards) {
        for (BoardDetails board : boards)
            printBoard (board);
    }

    /**
     * Method to print only this player's oard
     * @param board to print
     */

    public void printBoard(BoardDetails board) {
        printer.printBoard(board);
    }

    /**
     * Method to ask which square the player wants to move to
     * @param paths that can be taken
     */

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

    /**
     * Method to ask which square the player wants to move to and what they want to grab from there
     * @param paths that can be taken
     * @param map is the list of square details
     */

    public void showPathsAndGrabOptions(List<Integer> paths, List<SquareDetails> map) {
        printer.printPaths (paths);
        boolean valid = false;
        while(!valid) {
            int parsed = this.parser.asyncParseInt (11);
            if (parsed != -1) {
                for (Integer i : paths) {
                    if (parsed == i) {
                        SquareDetails s = map.get (0);
                        for (SquareDetails square : map)
                            if (square.getId () == i) {
                                s = square;
                                break;
                            }
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

    /**
     * Method to ask which weapon the player wants to grab from the spawn point
     * @param square is the spawn point
     */

    private void chooseWeapon(SpawnPointDetails square) {
        NewPositionAndGrabbed newPositionAndGrabbed;
        this.printer.printWeaponListToChoose (square.getWeaponsOnSquare ());
        int parsed = this.parser.asyncParseInt (2);
        if (parsed != -1) {
            newPositionAndGrabbed = new NewPositionAndGrabbed (nickname, square.getId (), square.getWeaponsOnSquare ()[parsed].getName ());
            sendToServer (newPositionAndGrabbed);
        }
    }

    /**
     * Method to ask the player which power up they want to use
     * @param powerUps is the list of power ups to choose from
     */

    public void choosePowerUp(List<PowerUpDetails> powerUps) {
        DataForServer powerUp;
        printer.printPowerUpList(powerUps, false);
        int parsed = this.parser.asyncParseInt (powerUps.size());
        if (parsed != -1) {
            if (parsed != powerUps.size()) {
                printer.printUseAsAmmo ( );
                int parsedBool = parser.asyncParseInt (1);
                if (parsedBool == 0)
                    powerUp = new ChosenPowerUp (nickname, powerUps.get (parsed).getType ( ), false);
                else
                    powerUp = new ChosenPowerUp (nickname, powerUps.get (parsed).getType ( ), true);
                sendToServer (powerUp);
            } else
                sendAction ("end action");
        }
    }

    /**
     * Method to ask the player which weapon they want to choose to shoot another player
     * @param weapons to choose from
     */

    public void chooseWeapon(List<WeaponDetails> weapons) {
        DataForServer weapon;
        printer.print("These are your loaded weapons: ");
        printer.printWeaponList (weapons);
        if (! weapons.isEmpty ()) {
            int parsed = this.parser.asyncParseInt (weapons.size ( ) - 1);
            if (parsed != -1) {
                weapon = new ChosenWeapon (nickname, weapons.get (parsed).getName ( ));
                sendToServer (weapon);
            }
        } else
            sendAction ("end action");
    }

    /**
     * Method to choose which effect to perform with the chosen weapon
     * @param effects to choose from
     */

    public void chooseWeaponEffect(List<EffectDetails> effects) {
        printer.print("Choose the effect you want to perform | press " + effects.size() + " to finish):");
        printer.printWeaponEffects(effects);
        int parsed = this.parser.asyncParseInt (effects.size());
        if (parsed != -1) {
            if (parsed != effects.size()) {
                DataForServer response = new ChosenEffect (nickname, parsed);
                sendToServer (response);
            } else
                sendAction ("end action");
        }
    }

    /**
     * Method to print all the targets' positions for the shoot action
     * @param targets to choose from
     * @param map is the list of square details
     * @return true if there are any targets, false otherwise
     */

    private boolean printPlayersPositions(List<String> targets, List<SquareDetails> map) {
        try {
            if (targets.isEmpty ()) {
                printer.print ("There are no players you can hit with this effect.\n");
                return false;
            } else {
                int i = 0;
                for (String name : targets) {
                    for (SquareDetails square : map) {
                        if (square.getPlayersOnSquare ( ).contains (name)) {
                            printer.printPlayerPositions (i, name, square.getId ( ));
                            break;
                        }
                    }
                    i++;
                }
                return true;
            }
        } catch (NullPointerException e) {
            printer.print ("This action does not require any targets.\n");
            return true;
        }
    }

    /**
     * Method to build all the atomic targets for the Shoot action
     * @param targets is the list of all atomic target types for the effect chosen
     * @param compliantTargets is the list of players that meet the requirements of the effect
     * @param map is the list of square details
     * @param hasTargetingScope is true if shooter has Targeting Scope, false otherwise
     */

    public void chooseTargets(List<TargetDetails> targets, List<String> compliantTargets, List<SquareDetails> map, boolean hasTargetingScope) {
        boolean invalid = false;
        boolean canTargetScope = false;
        List<AtomicTarget> chosenTargets = new ArrayList<> ();
        AtomicTarget atomicTarget = null;
        for (TargetDetails target : targets) {
            if (target.getValue () != -1) {
                //normal targets (single or multiple)
                atomicTarget = chooseTargets (target.getValue ( ), target.getMovements ( ), target.isArea ( ), compliantTargets, map);
                if (atomicTarget != null)
                    canTargetScope = true;
            } else if (target.getValue () == -1 && target.isArea ()) {
                //area based damage (square or room)
                atomicTarget = chooseAreaToTarget (compliantTargets, map);
                if (atomicTarget != null)
                    canTargetScope = true;
            } else if (target.getValue () == -1 && !target.isArea () && target.getMovements () == -1)
                //effect only needs to be applied, no player options
                atomicTarget = new AtomicTarget (null, -1);
            else if (target.getValue () == -1 && !target.isArea () && target.getMovements () > 0)
                //movement applied to attacker
                atomicTarget = chooseHowManyMovements(target.getMovements (), compliantTargets, map);
            //quit the loop if the timer runs out during one of the phases of target building/automatically pass the turn if the effect chosen can't hit anyone
            if (atomicTarget == null) {
                invalid = true;
                sendAction ("end action");
                printer.print("Error: you built an illegal action. Automatically skipping this action...");
                break;
            }
            else
                chosenTargets.add(atomicTarget);
        }
        if (!invalid) {
            String targetingScopeTarget = null;
            if (hasTargetingScope && canTargetScope)
                targetingScopeTarget = chooseTargetingScopeTarget(compliantTargets, map);
            sendToServer (new ChosenTargets (nickname, chosenTargets, targetingScopeTarget));
        }
    }

    /**
     * Method to ask the player who they want to hit with the Targeting Scope
     * @param targets is the list of targets to choose from
     * @param map is the list of square details
     * @return the name of the target chosen
     */

    private String chooseTargetingScopeTarget(List<String> targets, List<SquareDetails> map) {
        printer.printTargetingScope();
        int parsed = parser.asyncParseInt (1);
        if (parsed == 1) {
            printer.print("Choose one of the targets you hit with your weapon:");
            printPlayersPositions (targets, map);
            int parsedTarget = this.parser.asyncParseInt (targets.size ( ));
            if (parsedTarget != -1) {
                return targets.get (parsedTarget);
            }
        }
        return null;
    }

    /**
     * Method to ask the player which players they want to hit with their weapon's effect
     * @param maxAmount of targets to choose
     * @param movements that can be performed with the action
     * @param isArea is true if the attack targets a whole area, false otherwise
     * @param compliantTargets is the list of targets that meet the effect's requirements
     * @param map is the list of square details
     * @return the built atomic target
     */

    private AtomicTarget chooseTargets(int maxAmount, int movements, boolean isArea, List<String> compliantTargets, List<SquareDetails> map) {
        if (printPlayersPositions (compliantTargets, map)) {
            printer.print ("Choose your targets | press " + compliantTargets.size ( ) + " to finish beforehand): ");
            LinkedList<String> targets = new LinkedList<> ( );
            int amountChosen = 0;
            while (amountChosen < maxAmount) {
                printer.printChooseTargets (maxAmount - amountChosen);
                int parsed = this.parser.asyncParseInt (compliantTargets.size ( ));
                if (parsed != -1) {
                    if (parsed != compliantTargets.size ( )) {
                        amountChosen++;
                        targets.add (compliantTargets.get (parsed));
                    } else
                        break;
                } else
                    return null;
            }
            if (amountChosen > 0) {
                if (movements != -1) {
                    printer.print ("Choose the square you want to move the targets to (max distance of " + movements + "):");
                    int parsed = this.parser.asyncParseInt (map.size ( ));
                    if (parsed == -1)
                        return null;
                    return new AtomicTarget (targets, parsed);
                } else if (isArea) {
                    int n = findTargetPos(targets.getFirst (), map);
                    return new AtomicTarget (targets, n);
                }
                return new AtomicTarget (targets, -1);
            } else
                return null;
        } else
            return null;
    }

    /**
     * Method to find the target's position from the list of square details
     * @param name is the target's name
     * @param map is the list of square details
     * @return the target's square id
     */

    private int findTargetPos(String name, List<SquareDetails> map) {
        for (SquareDetails square : map) {
            if (square.getPlayersOnSquare ( ).contains (name)) {
                return square.getId ();
            }
        }
        return -1;
    }

    /**
     * Method to choose the square or room to target with the weapon's effect
     * @param compliantTargets is the list of targets that meet the requirements
     * @param map is the list of square details
     * @return the built atomic target
     */

    private AtomicTarget chooseAreaToTarget(List<String> compliantTargets, List<SquareDetails> map) {
        if (printPlayersPositions (compliantTargets, map)) {
            printer.print ("Type the id of the square you want to target/the id of a square in the room you want to target: ");
            int parsed = parser.asyncParseInt (map.size ( ));
            if (parsed != -1) {
                return new AtomicTarget (null, parsed);
            } else
                return null;
        } else
            return null;
    }

    /**
     * Method to choose which square to move to with shoot action
     * @param max amount of movements
     * @param compliantTargets is the list of compliant targets
     * @param map is the list of square details
     * @return the built atomic target
     */

    private AtomicTarget chooseHowManyMovements(int max, List<String> compliantTargets, List<SquareDetails> map) {
        if (printPlayersPositions (compliantTargets, map)) {
            printer.print ("Choose the square you want to move to (max distance of " + max + "):");
            int parsed = parser.asyncParseInt (map.size ( ));
            if (parsed != -1) {
                return new AtomicTarget (null, parsed);
            } else
                return null;
        } else
            return null;
    }

    /**
     * Method to choose a target and ask which square the player wants to move their target to
     * @param targets is the list of possible targets
     * @param map is the list of square details
     * @param possiblePaths is the list of possible paths
     */

    public void chooseSquareForTarget(List<String> targets, List<SquareDetails> map, Map<String, List<Integer>> possiblePaths) {
        printPlayersPositions (targets, map);
        printer.print ("Choose your target:");
        int parsedTarget = parser.asyncParseInt (targets.size()-1);
        if (parsedTarget != -1) {
            boolean valid = false;
            while (!valid) {
                printer.print ("Choose the square you want to move them to: ");
                printer.print(possiblePaths.get(targets.get (parsedTarget)) + "");
                int parsedSquare = parser.asyncParseInt (map.size ( ) - 1);
                if (parsedSquare != -1) {
                    if (possiblePaths.get(targets.get (parsedTarget)).contains (parsedSquare)) {
                        valid = true;
                        DataForServer powerUpEffect = new ChosenPowerUpEffect (nickname, targets.get (parsedTarget), parsedSquare);
                        sendToServer (powerUpEffect);
                    } else
                        printer.printInvalidInput ( );
                } else
                    break;
            }
        }
    }

    /**
     * Method to ask the player which square they want to move to
     * @param validSquareIds is the list of valid squares
     * @param weaponDetails is the list of weapons to choose from
     */

    public void chooseSquare(List<Integer> validSquareIds, List<WeaponDetails> weaponDetails) {
        boolean valid = false;
        int parsed = -1;
        while (!valid) {
            printer.print (validSquareIds + "");
            printer.print ("Choose the square you want to move to: ");
            parsed = parser.asyncParseInt (11);
            if (parsed != -1) {
                if (validSquareIds.contains (parsed)) {
                    valid = true;
                } else
                    printer.printInvalidInput ();
            } else
                break;
        }
        if (valid && weaponDetails == null) {
            DataForServer powerUpEffect = new ChosenPowerUpEffect (nickname, parsed);
            sendToServer (powerUpEffect);
        } else if (valid && weaponDetails.isEmpty ()) {
            printer.print("You have no loaded weapons");
            sendAction ("end action");
        } else if (valid)
            chooseWeapon (weaponDetails);
    }

    /**
     * Method to ask the player which weapon they want to reload
     * @param ammo is the list of owned ammo
     * @param weapons to choose from
     */

    public void askReload(List<String> ammo, List<WeaponDetails> weapons, boolean isBeforeShoot) {
        printer.printReload();
        int parsed = this.parser.asyncParseInt (1);
        if (parsed != -1) {
            if (parsed == 0) {
                DataForServer response = new ReloadResponse(nickname, false, "", isBeforeShoot);
                sendToServer (response);
            } else if (parsed == 1) {
                printer.print ("Owned ammo: " + ammo);
                printer.print ("These are your unloaded weapons: ");
                printer.printWeaponList (weapons);
                int parsed1 = this.parser.asyncParseInt (weapons.size ());
                if (parsed1 != -1) {
                    DataForServer response = new ReloadResponse (nickname, true, weapons.get(parsed1).getName (), isBeforeShoot);
                    sendToServer (response);
                }
            }
        }

    }

    /**
     * Method to ask the player which weapon they want to discard
     * @param weapons to choose from
     */

    public void discardWeapon(List<WeaponDetails> weapons) {
        printer.printWeaponList (weapons);
        int parsed = this.parser.asyncParseInt (weapons.size ( )-1);
        if (parsed != -1) {
            DataForServer response = new DiscardedWeapon (nickname, weapons.get (parsed).getName ( ));
            sendToServer (response);
        }
    }

    /**
     * Method to ask the player if they want to use Tagback Grenade
     * @param attacker is the name of the player that hit this player
     */

    public void askTagback(String attacker) {
        parser.setActive (true);
        printer.printTagback(attacker);
        DataForServer response;
        int parsed = this.parser.asyncParseInt (1);
        if (parsed != -1) {
            if (parsed == 0)
                response = new TagbackResponse (nickname, attacker, false);
            else
                response = new TagbackResponse (nickname, attacker,  true);
            sendToServer (response);
        } else {
            response = new TagbackResponse (nickname, attacker, false);
            sendToServer (response);
        }
    }

    /**
     * Method to show the end game screen
     * @param ranking is the ranking of the game
     */

    public void showEndGameScreen(List<String> ranking) {
        printer.printRanking (ranking);
        printer.printEndGame ();
        System.exit (0);
    }
}
