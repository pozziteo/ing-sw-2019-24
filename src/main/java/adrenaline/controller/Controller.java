package adrenaline.controller;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.data_for_game.*;
import adrenaline.data.data_for_client.data_for_network.MessageForClient;
import adrenaline.data.data_for_client.responses_for_view.WeaponDetails;
import adrenaline.data.data_for_server.data_for_game.DataForController;
import adrenaline.exceptions.MustDiscardWeaponException;
import adrenaline.exceptions.NotEnoughAmmoException;
import adrenaline.model.GameModel;
import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.OptionalEffect;
import adrenaline.model.deck.Weapon;
import adrenaline.model.map.SpawnPoint;
import adrenaline.model.player.*;
import adrenaline.network.Lobby;
import adrenaline.utils.ConfigFileReader;
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

    //path for default map
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";

    public Controller(Lobby lobby) {
        this.lobby = lobby;
        this.timeout = ConfigFileReader.readConfigFile("controllerTimeout");
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
            InitialSpawnPointSetUp data = new InitialSpawnPointSetUp(gameModel.createPowerUpDetails (p));
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
    }

    public void informOfDisconnection(String nickname) {
        if (!gameModel.getGame ().isEndGame ()) {
            if (gameModel.getGame ().getCurrentTurn () == 0) {
                gameModel.getGame ().getPlayers ().remove(gameModel.getGame ().findByNickname (nickname));
                if (checkPlayersReady()) {
                    System.out.println("All players have spawned...\n");
                    lobby.sendMessageToAll("All players have spawned.\n");
                    gameModel.getGame ().startGame();
                    playNewTurn ();
                }
            } else {
                int indexOfLast = gameModel.getGame ( ).getPlayers ( ).size ( ) - 1;
                int currentTurn = gameModel.getGame ( ).getCurrentTurn ( ) - 1;
                String currentPlayer = gameModel.getGame ( ).getPlayers ( ).get (indexOfLast - currentTurn).getPlayerName ( );
                if (nickname.equals (currentPlayer)) {
                    timer.shutDownThread ( );
                    for (Player p : gameModel.getGame ( ).getPlayers ( )) {
                        if (p.getPlayerName ( ).equals (nickname)) {
                            dummyPlayers.add (p);
                            break;
                        }
                    }
                    gameModel.getGame ( ).incrementTurn ( );
                    playNewTurn ( );
                } else {
                    for (Player p : gameModel.getGame ( ).getPlayers ( )) {
                        if (p.getPlayerName ( ).equals (nickname)) {
                            dummyPlayers.add (p);
                            break;
                        }
                    }
                }
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
        lobby.sendMessageToAll (nickname + " has been added back to the game...\n");
    }

    public void endGameBecauseOfDisconnection() {
        gameModel.getGame ().setEndGame (true);
        timer.shutDownThread ();
        lobby.sendMessageToAll ("Ending the game because of lack of players...");

        List<String> ranking = new ArrayList<> ();
        for (Player p : gameModel.getGame ().getRanking ())
            ranking.add(p.getPlayerName ());

        for (Player p : gameModel.getGame ().getPlayers ()) {
            if (! dummyPlayers.contains(p)) {
                lobby.sendToSpecific (p.getPlayerName (), new EndGameStatus (ranking));
            }
        }
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
                options = new MovementAndGrabOptions(((MoveAndGrab) currentAction).getPaths(), gameModel.createSquareDetails ());
                lobby.sendToSpecific(nickname, options);
                break;
            case "shoot":
                this.currentAction = new Shoot();
                gameModel.getGame ().setCurrentAction(currentAction);
                List<WeaponDetails> weaponDetails = new ArrayList<> ();
                for (Weapon w : gameModel.getGame ().findByNickname (nickname).getOwnedWeapons ())
                    weaponDetails.add(gameModel.createWeaponDetail (w));
                options = new ShootOptions(weaponDetails);
                lobby.sendToSpecific (nickname, options);
                break;
            case "power up":
                UsePowerUp usePup = new UsePowerUp(nickname);
//                usePup.usePowerUp(powerUp);
                break;
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
        else if (currentAction instanceof MoveAndGrab) {
            try {
                ((MoveAndGrab) currentAction).grabObject (p, squareId, null);
            } catch (NotEnoughAmmoException | MustDiscardWeaponException e) {
                System.err.print (e.getMessage ());
            }
        }
        checkNewTurn (nickname);
    }

    public void executeAction(String nickname, int squareId, String weaponName) {
        Player p = gameModel.getGame ().findByNickname (nickname);
        Weapon weapon = null;
        for (Weapon w : ((SpawnPoint) gameModel.getGame ().getMap ().getSquare (squareId)).getWeapons ()) {
            try {
                if (w.getWeaponsName ( ).equals (weaponName)) {
                    weapon = w;
                    break;
                }
            } catch (NullPointerException e) {
                //empty weapon slot
            }
        }
        try {
            ((MoveAndGrab) currentAction).grabObject (p, squareId, weapon);
        } catch (NotEnoughAmmoException e) {
            lobby.sendToSpecific (nickname, new MessageForClient (e.getMessage()));
        } catch (MustDiscardWeaponException e) {
            List<WeaponDetails> weapons = new ArrayList<> ();
            for (Weapon w : gameModel.getGame ().findByNickname (nickname).getOwnedWeapons ())
                weapons.add(gameModel.createWeaponDetail (w));
            for (Weapon w : gameModel.getGame ().findByNickname (nickname).getBoard ().getUnloadedWeapons ())
                weapons.add(gameModel.createWeaponDetail (w));

            lobby.sendToSpecific (nickname, new WeaponsToDiscard(weapons));
        }
        checkNewTurn (nickname);
    }

    public void executeAction(String attackerName, List<String> targetsNames, String weaponName) {
        Player attacker = gameModel.getGame ().findByNickname (attackerName);
        List<Player> targets = new LinkedList<> ();
        for (String nickname : targetsNames) {
            targets.add(gameModel.getGame ().findByNickname (nickname));
        }
        Weapon weapon = gameModel.getGame ().findByNickname (attackerName).findLoadedWeapon (weaponName);
        ((Shoot)currentAction).performAttack (attacker, targets, weapon);
        checkNewTurn (attackerName);
    }

    private void checkNewTurn(String nickname) {
        if (isFirstAction ()) {
            lobby.sendToSpecific (nickname, new Turn(nickname));
            lobby.sendToAllNonCurrent (nickname, new Turn(nickname));
        } else if (! gameModel.getGame ().findByNickname (nickname).getBoard ().getUnloadedWeapons ().isEmpty ()){
            askReload (nickname);
        } else {
            playNewTurn ();
        }
    }

    private void askReload(String nickname) {
        List<String> ammo = new ArrayList<> ();
        List<WeaponDetails> weapons = new ArrayList<> ();
        Player p = gameModel.getGame ().findByNickname (nickname);

        for (Ammo a : p.getBoard ().getOwnedAmmo ())
            ammo.add(a.getColor ());

        for (Weapon w : p.getBoard ().getUnloadedWeapons ()) {
            weapons.add(gameModel.createWeaponDetail (w));
        }

        lobby.sendToSpecific (nickname, new ReloadRequest(ammo, weapons));
    }

    public void reloadWeapon(String nickname, boolean positive, String weaponName) {
        if (positive) {
            try {
                gameModel.getGame ().findByNickname (nickname).reloadWeapon (gameModel.getGame ().findByNickname (nickname).findUnloadedWeapon (weaponName));
            } catch (NotEnoughAmmoException e) {
                lobby.sendToSpecific (nickname, new MessageForClient (e.getMessage ()));
            }
            askReload(nickname);
        } else
            playNewTurn ();
    }

    public void discardWeapon(String nickname, String weaponName) {
        Weapon weapon = gameModel.getGame ().findByNickname (nickname).findLoadedWeapon (weaponName);
        if (weapon == null) {
            weapon = gameModel.getGame ().findByNickname (nickname).findUnloadedWeapon (weaponName);
        }
        if (weapon != null) {
            gameModel.getGame ().replaceWeapon(weapon);
        }
    }

    private boolean isFirstAction() {
        return (gameModel.getGame ().getCurrentTurnActionNumber () == 1);
    }

    public void sendModeOptions(String nickname, String weaponName) {
        Weapon weapon = gameModel.getGame ().findByNickname (nickname).findLoadedWeapon (weaponName);
        ((Shoot) currentAction).setChosenWeapon (weapon);
        if (weapon != null) {
            lobby.sendToSpecific (nickname, new WeaponModeOptions (gameModel.createWeaponEffects (weapon)));
        }
    }

    public void askTargets(String nickname, int effectId) {
        TargetOptions options = null;
        if (effectId == 0) {
            ((Shoot) currentAction).addEffectToApply (((Shoot) currentAction).getChosenWeapon ( ).getBaseEffect ( ), true);
            options = new TargetOptions (gameModel.createEffectDetails (((Shoot) currentAction).getChosenWeapon ( ).getBaseEffect ( )));
        } else {
            for (OptionalEffect e : ((Shoot)currentAction).getChosenWeapon ().getOptionalEffects ()) {
                effectId--;
                if (effectId == 0) {
                    ((Shoot) currentAction).addEffectToApply (e, false);
                    options = new TargetOptions (gameModel.createEffectDetails (e));
                }
            }
        }
        if (((Shoot)currentAction).getChosenWeapon ().getOptionalEffects ().get(0).isAlternativeMode ()) {
            ((Shoot)currentAction).setEndAction(true);
        }
        lobby.sendToSpecific (nickname, options);
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
