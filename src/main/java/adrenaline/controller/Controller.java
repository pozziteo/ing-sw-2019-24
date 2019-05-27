package adrenaline.controller;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.data_for_game.*;
import adrenaline.data.data_for_client.data_for_network.MessageForClient;
import adrenaline.data.data_for_server.data_for_game.DataForController;
import adrenaline.model.GameModel;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.player.*;
import adrenaline.network.Lobby;
import adrenaline.utils.TimerCallBack;
import adrenaline.utils.TimerThread;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Controller implements TimerCallBack {
    private Lobby lobby;
    private GameModel gameModel;
    private long timeout;
    private TimerThread timer;
    private ArrayList<Player> dummyPlayers;
    private Action currentAction;
    private PowerUp powerUp;

    //path for default map
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";

    public Controller(Lobby lobby) {
        this.lobby = lobby;
        this.timeout = lobby.readConfigFile("controllerTimeout");
        this.timer = new TimerThread (this, timeout);
        this.dummyPlayers = new ArrayList<> ();
    }

    public GameModel getGameModel() {
        return this.gameModel;
    }

    public Lobby getLobby() {
        return this.lobby;
    }

    public void receiveData(DataForController data) {
        Runnable thread = () -> {
            Thread.currentThread ().setName ("Controller Receiver Thread");
            System.out.println("Received " + data.getClass ().getSimpleName ());
            data.updateGame (this);
        };
        Thread receiverThread = new Thread(thread);
        receiverThread.start();
    }

    public void startController(GameModel model) {
        this.gameModel = model;
        mapSetUp ();
    }

    private void mapSetUp() {
        timer.startThread ();
        int indexOfLast = gameModel.getGame ().getPlayers ().size ()-1;
        Player firstPlayer = gameModel.getGame ().getPlayers ().get (indexOfLast); //clients are put into the list of players in reverse order
        MapSetUp data = new MapSetUp ();
        lobby.sendToSpecific (firstPlayer.getPlayerName (), data);
        for (Player p : gameModel.getGame ().getPlayers ().subList (0, indexOfLast)) {
            lobby.sendToSpecific (p.getPlayerName (), new MessageForClient ("The first player in your lobby is choosing the arena...\n"));
        }
    }

    public void initializeMap(String filepath) {
        gameModel.getGame ().setArena (filepath);
        lobby.sendMessageToAll ("Map has been initialized to " + gameModel.getGame ().getMap ().getMapName ());
        spawnPointSetUp ();
    }

    private void spawnPointSetUp() {
        timer.shutDownThread ();
        for (Player p : gameModel.getGame ().getPlayers ()) {
            InitialSpawnPointSetUp data = new InitialSpawnPointSetUp(p.getOwnedPowerUps ());
            lobby.sendToSpecific (p.getPlayerName (), data);
        }
    }

    public synchronized void setSpawnPoint(String nickname, String color){
        System.out.print ("Setting spawn point for " + nickname + "...\n");
        Player p = gameModel.getGame ().findByNickname(nickname);
        if (p == null) {
            System.err.print("Player named " + nickname + " not found...\n");
        } else {
            p.chooseSpawnPoint (color);
        }
        if (checkPlayersReady()) {
            System.out.println("All players have spawned...\n");
            lobby.sendMessageToAll("All players have spawned.\n");
            gameModel.getGame ().startGame();
            playNewTurn ();
        }
    }

    private boolean checkPlayersReady() {
        boolean ready = true;
        for (Player p : gameModel.getGame ().getPlayers ()) {
            if (p.getPosition () == null) {
                ready = false;
                break;
            }
        }
        return ready;
    }

    private void playNewTurn() {
        timer.shutDownThread ();
        if (! gameModel.getGame ().isEndGame()) {
            if (! gameModel.getGame ().isFinalFrenzy ()) {
                int indexOfLast = gameModel.getGame ().getPlayers ().size ()-1;
                int currentTurn = gameModel.getGame ( ).getCurrentTurn ( );
                String currentPlayer;
                if (dummyPlayers.contains(gameModel.getGame ().getPlayers ().get (indexOfLast - currentTurn))) {
                    gameModel.getGame ().incrementTurn ();
                    currentTurn = gameModel.getGame ().getCurrentTurn ( );
                }
                currentPlayer = gameModel.getGame ().getPlayers ().get (indexOfLast - currentTurn).getPlayerName ( );
                lobby.sendToSpecific (currentPlayer, new Turn(currentPlayer));
                lobby.sendToAllNonCurrent (currentPlayer, new Turn(currentPlayer));
                timer.startThread (currentPlayer);
                gameModel.getGame ().incrementTurn ( );
            } else {
                //TODO
            }
        } else {
            //TODO
        }
    }

    public void informOfDisconnection(String nickname) {
        for (Player p : gameModel.getGame ().getPlayers ()) {
            if (p.getPlayerName ().equals(nickname)) {
                dummyPlayers.add (p);
                break;
            }
        }
    }

    public void informOfReconnection(String nickname) {
        for (Player p : dummyPlayers) {
            if (p.getPlayerName ().equals (nickname)) {
                dummyPlayers.remove (p);
                break;
            }
        }
    }

    public void endGame() {
        gameModel.getGame ().setEndGame (true);
        //TODO
    }

    public void buildAction(String type, String nickname) {
        Player p = gameModel.getGame ().findByNickname (nickname);
        DataForClient options;
        switch (type) {
            case "move":
                this.currentAction = new Move(p, gameModel.getGame ().isFinalFrenzy ());
                gameModel.getGame ().setCurrentAction(currentAction);
                options = new MovementOptions (((Move) currentAction).getPaths ());
                lobby.sendToSpecific (nickname, options);
                break;
            case "move and grab":
                this.currentAction = new MoveAndGrab(p, gameModel.getGame().isFinalFrenzy());
                gameModel.getGame ().setCurrentAction(currentAction);
                options = new MovementAndGrabOptions(((MoveAndGrab) currentAction).getPaths(), gameModel.getGame ().getMap ());
                lobby.sendToSpecific(nickname, options);
                break;
            case "shoot":
                this.currentAction = new Shoot(p);
                gameModel.getGame ().setCurrentAction(currentAction);
                options = new ShootOptions();
                lobby.sendToSpecific (nickname, options);
                break;
            case "power up":
                UsePowerUp usePup = new UsePowerUp(nickname);
//                usePup.usePowerUp(powerUp);
                break;
//            case "ammo":
//                UsePowerUp useAmmoFromPup = new UsePowerUp(nickname);
//                useAmmoFromPup.useAmmo();
//
            case "pass":
                timer.shutDownThread();
                lobby.sendMessageToAll(nickname + " passed the turn.\n");
                playNewTurn();
                break;
            default:
                break;
        }
    }

    public void executeAction(String nickname, int squareId) {
        Player p = gameModel.getGame ().findByNickname (nickname);
        if (currentAction instanceof  Move)
            ((Move)currentAction).performMovement (p, squareId);
        else if (currentAction instanceof MoveAndGrab)
            ((MoveAndGrab)currentAction).grabObject(p, squareId, null);
        checkNewTurn (nickname);
    }

    public void executeAction(String nickname, int squareId, Weapon w) {
        Player p = gameModel.getGame ().findByNickname (nickname);
        ((MoveAndGrab)currentAction).grabObject(p, squareId, w);
        checkNewTurn (nickname);
    }

    public void executeAction(String attackerName, List<String> targetsNames, Weapon weapon) {
        Player attacker = gameModel.getGame ().findByNickname (attackerName);
        List<Player> targets = new LinkedList<> ();
        for (String nickname : targetsNames) {
            targets.add(gameModel.getGame ().findByNickname (nickname));
        }
        ((Shoot)currentAction).performAttack (attacker, targets, weapon);
        checkNewTurn (attackerName);
    }

    private void checkNewTurn(String nickname) {
        if (isFirstAction ()) {
            lobby.sendToSpecific (nickname, new Turn(nickname));
            lobby.sendToAllNonCurrent (nickname, new Turn(nickname));
        } else
            playNewTurn ();
    }

    private boolean isFirstAction() {
        return (gameModel.getGame ().getCurrentTurnActionNumber () == 1);
    }

    //******************************************************************************************************************
    // CALLBACK
    //******************************************************************************************************************

    /**
     * Callback function for map set up time out. If the first player doesn't select a map in the set amount
     * of time, it gets initialized to the default one (small).
     */

    @Override
    public void timerCallBack() {
        gameModel.getGame ( ).setArena (SMALL);
        lobby.sendMessageToAll ("The arena has been set to the default one (small arena)\n");
        TimeOutNotice notice = new TimeOutNotice (lobby.getPlayers ().get (0));
        notice.sendToView ();
        spawnPointSetUp ();
    }

    /**
     * Callback function for turn time out.
     * @param nickname of the player that didn't make a move in the set amount of time.
     */

    @Override
    public void timerCallBack(String nickname) {
        TimeOutNotice notice = new TimeOutNotice (lobby.findPlayer(nickname));
        notice.sendToView ();
        playNewTurn ();
    }
}
