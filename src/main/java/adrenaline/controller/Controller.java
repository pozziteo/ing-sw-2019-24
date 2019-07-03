package adrenaline.controller;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.data_for_game.*;
import adrenaline.data.data_for_client.data_for_network.MessageForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.SquareDetails;
import adrenaline.data.data_for_client.responses_for_view.fake_model.WeaponDetails;
import adrenaline.data.data_for_server.data_for_game.AtomicTarget;
import adrenaline.data.data_for_server.data_for_game.DataForController;
import adrenaline.exceptions.IllegalUseOfPowerUpException;
import adrenaline.exceptions.MustDiscardWeaponException;
import adrenaline.exceptions.NotEnoughAmmoException;
import adrenaline.exceptions.IllegalTargetException;
import adrenaline.model.GameModel;
import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.OptionalEffect;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.powerup.PowerUpEffect;
import adrenaline.model.map.SpawnPoint;
import adrenaline.model.player.*;
import adrenaline.network.Lobby;
import adrenaline.utils.ConfigFileReader;
import adrenaline.utils.TimerCallBack;
import adrenaline.utils.TimerThread;

import java.io.File;
import java.util.*;

/**
 * This class implements the controller part of the MVC design pattern.
 * An instance is created when a new game begins, and then data is received and sent using
 * command pattern. When the player timer runs out, this class is used as callback.
 */

public class Controller implements TimerCallBack {
    private Lobby lobby;
    private GameModel gameModel;
    private TimerThread timer;
    private ArrayList<Player> dummyPlayers; //list of players that disconnected during a game
    private Action currentAction;
    private PowerUpEffect powerUpEffect;

    //path for default map
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";

    public Controller(Lobby lobby) {
        this.lobby = lobby;
        this.timer = new TimerThread (this, ConfigFileReader.readConfigFile("controllerTimeout"));
        this.dummyPlayers = new ArrayList<> ();
    }

    /**
     * Getter method to obtain the game model instance
     * @return model
     */

    public GameModel getGameModel() {
        return this.gameModel;
    }

    /**
     * Getter method to obtain lobby of this controller's game
     * @return
     */

    public Lobby getLobby() {
        return this.lobby;
    }

    /**
     * Method to create a thread each time a new event is received
     * @param data received from Client
     */

    public void receiveData(DataForController data) {
        Runnable thread = () -> {
            Thread.currentThread ().setName ("Controller Receiver Thread");
            System.out.println("Received " + data.getClass ().getSimpleName () + " in lobby #" + lobby.getId());
            data.updateGame (this);
        };
        Thread receiverThread = new Thread(thread);
        receiverThread.start();
    }

    /**
     * Method to set the model for the game
     * @param model of game
     */

    public void startController(GameModel model) {
        this.gameModel = model;
        mapSetUp ();
    }

    /**
     * Method to request which map the match will be played on to first player
     */

    private void mapSetUp() {
        timer.startThread ();
        int indexOfLast = gameModel.getGame ().getPlayers ().size ()-1;
        Player firstPlayer = gameModel.getGame ().getPlayers ().get (indexOfLast); //clients are put into the list of players in reverse order

        Map<String, String> playerColors = new HashMap<>();
        for (Player p : gameModel.getGame().getPlayers()) {
            playerColors.put(p.getPlayerName(), p.getPlayerColor());
        }
        Map<String, String> readOnlyColors = Collections.unmodifiableMap(playerColors);

        for (Player p : gameModel.getGame ().getPlayers ()) {
            lobby.sendToSpecific (p.getPlayerName (), new MapSetUp (firstPlayer.getPlayerName (), readOnlyColors));
        }
    }

    /**
     * Method to set the chosen map and notify the choice to all players
     * @param filepath of json file of the chosen map
     */

    public void initializeMap(String filepath) {
        gameModel.getGame ().setArena (filepath);
        lobby.sendToAll (new MapInfo(filepath, gameModel.getGame ().getMap ().getMapName ()));
        spawnPointSetUp ();
    }

    /**
     * Method to request the spawn point to all clients
     */

    private void spawnPointSetUp() {
        timer.shutDownThread ();
        for (Player p : gameModel.getGame ().getPlayers ()) {
            SpawnPointSetUp data = new SpawnPointSetUp (gameModel.createPowerUpDetails (p));
            lobby.sendToSpecific (p.getPlayerName (), data);
        }
    }

    /**
     * Method to set the spawn point for each client.
     * When all clients have spawned, the game starts
     * @param nickname of the client that chose their spawn point
     * @param color of the chosen spawn point's room
     */

    public synchronized void setSpawnPoint(String nickname, String color){
        System.out.print ("Setting spawn point for " + nickname + "...\n");
        Player p = gameModel.getGame ().findByNickname(nickname);
        if (p == null) {
            System.err.print("Player named " + nickname + " not found...\n");
        } else {
            p.chooseSpawnPoint (color);
        }
        if (!gameModel.getGame ().isStartGame () && checkPlayersReady()) {
            System.out.println("All players have spawned...\n");
            lobby.sendMessageToAll("All players have spawned.\n");
            gameModel.getGame ().startGame();
            playNewTurn ();
        } else if (gameModel.getGame ().isStartGame ()) {
            lobby.sendToSpecific (nickname, new Turn (nickname));
        }
    }

    /**
     * Method to check if all players in the game have spawned
     * @return true if all players have spawned, false otherwise
     */

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

    /**
     * Method to start a new turn. Used each time a player finishes their turn,
     * passes their turn or the timer runs out. If the player that should play
     * the new turn went offline, the turn passes automatically.
     * Starts a new timer.
     */

    private void playNewTurn() {
        if (gameModel.getGame ().isFinalFrenzy () && gameModel.getGame ().getCurrentTurn () == lobby.getPlayers ().size()) {
            gameModel.getGame ( ).setEndGame (true);
        }
        timer.shutDownThread ();
        gameModel.resetCanTagback();
        int indexOfLast = gameModel.getGame ().getPlayers ().size ()-1;
        int currentTurn = gameModel.getGame ( ).getCurrentTurn ( );
        Player currentPlayer;
        if (dummyPlayers.contains(gameModel.getGame ().getPlayers ().get (indexOfLast - currentTurn))) {
            gameModel.getGame ().incrementTurn ();
            currentTurn = gameModel.getGame ().getCurrentTurn ( );
        }
        currentPlayer = gameModel.getGame ().getPlayers ().get (indexOfLast - currentTurn);
        lobby.sendToAllNonCurrent (currentPlayer.getPlayerName (), new Turn(currentPlayer.getPlayerName ()));
        if (!currentPlayer.isWaitingForRespawn()) {
            lobby.sendToSpecific (currentPlayer.getPlayerName (), new Turn(currentPlayer.getPlayerName ()));
        } else {
            lobby.sendToSpecific (currentPlayer.getPlayerName (), new SpawnPointSetUp (gameModel.createPowerUpDetails (currentPlayer)));
        }
        timer.startThread (currentPlayer.getPlayerName ());
        gameModel.getGame ().incrementTurn ( );
        if (gameModel.getGame ().isEndGame ())
            endGame();
    }

    /**
     * Method to handle player disconnection during game.
     * If the disconnection occurs before all players have spawned, the client is removed from
     * the players' list in the game model.
     * Otherwise, the player is added to the list of dummy players.
     * @param nickname of disconnected client
     */

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

    /**
     * Method to add a player that reconnected back into the game by removing them
     * from the list of dummy players.
     * @param nickname of reconnected client
     */

    public void informOfReconnection(String nickname) {
        for (Player p : dummyPlayers) {
            if (p.getPlayerName ().equals (nickname)) {
                dummyPlayers.remove (p);
                break;
            }
        }
        lobby.sendMessageToAll (nickname + " has been added back to the game...\n");
    }

    public void endGame() {
        timer.shutDownThread ();

        List<String> ranking = new ArrayList<> ();
        for (Player p : gameModel.getGame ().getRanking ())
            ranking.add(p.getPlayerName ());

        for (Player p : gameModel.getGame ().getPlayers ()) {
            if (! dummyPlayers.contains(p)) {
                lobby.sendToSpecific (p.getPlayerName (), new EndGameStatus (ranking));
            }
        }
    }

    /**
     * Method to end the game if the amount of players left is less than three
     */

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

    /**
     * Method to build a new action during player turn.
     * @param type of action to build
     * @param nickname of the player building the action
     */

    public void buildAction(String type, String nickname) {
        Player p = gameModel.getGame ().findByNickname (nickname);
        DataForClient options;
        switch (type) {
            case "move":
                this.currentAction = new MoveAction (p, gameModel.getGame ().isFinalFrenzy ());
                gameModel.getGame ().setCurrentAction(currentAction);
                options = new MovementOptions (((MoveAction) currentAction).getPaths ());
                lobby.sendToSpecific (nickname, options);
                break;
            case "move and grab":
                this.currentAction = new MoveAndGrabAction (p, gameModel.getGame().isFinalFrenzy());
                gameModel.getGame ().setCurrentAction(currentAction);
                options = new MovementAndGrabOptions(((MoveAndGrabAction) currentAction).getPaths(), gameModel.createSquareDetails ());
                lobby.sendToSpecific(nickname, options);
                break;
            case "shoot":
                this.currentAction = new ShootAction (gameModel.getGame ().findByNickname (nickname));
                gameModel.getGame ().setCurrentAction(currentAction);
                List<WeaponDetails> weaponDetails = new ArrayList<> ();
                for (Weapon w : gameModel.getGame ().findByNickname (nickname).getOwnedWeapons ())
                    weaponDetails.add(gameModel.createWeaponDetail (w));
                options = new ShootOptions(((ShootAction)currentAction).isAdrenaline (), Action.findPaths (gameModel.getGame ().findByNickname (nickname), 1), weaponDetails);
                lobby.sendToSpecific (nickname, options);
                break;
            case "power up":
                options = new PowerUpOptions(gameModel.createPowerUpDetails (gameModel.getGame ().findByNickname (nickname)));
                lobby.sendToSpecific (nickname, options);
                break;
            case "pass":
                timer.shutDownThread ( );
                lobby.sendMessageToAll (nickname + " passed the turn.\n");
                playNewTurn ( );
                break;
            case "end action":
                checkNewTurn (nickname);
                break;
            default:
                break;
        }
    }

    /**
     * Method to execute a Move or MoveAndGrabAction (if the square chosen is not a
     * spawn point) after the client chose the
     * square to move to.
     * @param nickname of player performing action
     * @param squareId of square to move to
     */

    public void executeAction(String nickname, int squareId) {
        Player p = gameModel.getGame ().findByNickname (nickname);
        if (currentAction instanceof MoveAction)
            ((MoveAction)currentAction).performMovement (p, squareId);
        else if (currentAction instanceof MoveAndGrabAction) {
            try {
                ((MoveAndGrabAction) currentAction).grabObject (p, squareId, null);
            } catch (NotEnoughAmmoException | MustDiscardWeaponException e) {
                //should not be thrown
            }
        }
        gameModel.getGame ().updateCurrentAction (currentAction);
        checkNewTurn (nickname);
    }

    /**
     * Method to execute a MoveAndGrabAction if the square chosen is a spawn point.
     * @param nickname of player performing the action
     * @param squareId of square to move to
     * @param weaponName of the weapon grabbed from spawn point
     */

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
            ((MoveAndGrabAction) currentAction).grabObject (p, squareId, weapon);
            gameModel.getGame ().updateCurrentAction (currentAction);
            checkNewTurn (nickname);
        } catch (NotEnoughAmmoException e) {
            lobby.sendToSpecific (nickname, new MessageForClient (e.getMessage()));
            checkNewTurn (nickname);
        } catch (MustDiscardWeaponException e) {
            lobby.sendToSpecific (nickname, new MessageForClient (e.getMessage ()));
            List<WeaponDetails> weapons = new ArrayList<> ();
            for (Weapon w : gameModel.getGame ().findByNickname (nickname).getOwnedWeapons ())
                weapons.add(gameModel.createWeaponDetail (w));
            for (Weapon w : gameModel.getGame ().findByNickname (nickname).getBoard ().getUnloadedWeapons ())
                weapons.add(gameModel.createWeaponDetail (w));

            lobby.sendToSpecific (nickname, new WeaponsToDiscard(weapons));
        }
    }

    /**
     * Method to check if the current turn is at the last action.
     * If it is, then a request to reload any unloaded weapons will be sent to the client.
     * If the client doesn't have any unloaded weapons or refuses to reload,
     * the turn passes to the next one.
     * @param nickname of current player
     */

    private void checkNewTurn(String nickname) {
        if (! isLastAction ()) {
            lobby.sendToSpecific (nickname, new Turn(nickname));
            lobby.sendToAllNonCurrent (nickname, new Turn(nickname));
        } else if (! gameModel.getGame ().findByNickname (nickname).getBoard ().getUnloadedWeapons ().isEmpty ()){
            askReload (nickname);
        } else {
            playNewTurn ();
        }
    }

    /**
     * Method to ask a player if they want to reload any weapons.
     * @param nickname of current player
     */

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

    /**
     * Method to reload selected weapon.
     * @param nickname of player reloading the weapon
     * @param positive true if the player wants to reload, false otherwise
     * @param weaponName of weapon to reload
     */

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

    /**
     * Method to discard a weapon if the limit of 3 is exceeded.
     * @param nickname of player discarding the weapon
     * @param weaponName of weapon to discard
     */

    public void discardWeapon(String nickname, String weaponName) {
        Weapon weapon = gameModel.getGame ().findByNickname (nickname).findLoadedWeapon (weaponName);
        if (weapon == null) {
            weapon = gameModel.getGame ().findByNickname (nickname).findUnloadedWeapon (weaponName);
        }
        if (gameModel.getGame ().findByNickname (nickname).getOwnedWeapons ().remove (weapon) || gameModel.getGame ().findByNickname (nickname).getBoard ().getUnloadedWeapons ().remove (weapon)) {
            gameModel.getGame ().replaceWeapon(gameModel.getGame ().findByNickname (nickname), weapon);
        }
        checkNewTurn (nickname);
    }

    /**
     * Method to check if the current turn is at the last action.
     * @return true if is last action, false otherwise
     */

    private boolean isLastAction() {
        return (gameModel.getGame ().getCurrentTurnActionNumber () == 2);
    }

    /**
     * Method to send the available weapon effects to the client.
     * @param nickname of current player
     * @param weaponName of weapon chosen to shoot
     */

    public void sendModeOptions(String nickname, String weaponName) {
        Weapon weapon = gameModel.getGame ().findByNickname (nickname).findLoadedWeapon (weaponName);
        ((ShootAction) currentAction).setChosenWeapon (weapon);
        gameModel.getGame ().updateCurrentAction (currentAction);
        if (weapon != null) {
            lobby.sendToSpecific (nickname, new WeaponModeOptions (gameModel.createWeaponEffects (weapon)));
        }
    }

    /**
     * Method to build the targets of the shoot action
     * @param nickname of current player
     * @param effectId of effect chosen
     */

    public void askTargets(String nickname, int effectId) {
        Player p = gameModel.getGame ().findByNickname (nickname);
        List<SquareDetails> map = gameModel.createSquareDetails ();
        TargetOptions options = null;
        if (effectId == 0 && !((ShootAction)currentAction).isBaseUsed ()) {
            options = setBaseEffect (nickname, map);
        } else {
            if (! ((ShootAction)currentAction).isMustUseBase ()) {
                for (OptionalEffect e : ((ShootAction) currentAction).getChosenWeapon ( ).getOptionalEffects ( )) {
                    effectId--;
                    if (effectId == 0) {
                        try {
                        if (e.isUsableBeforeBase ( ) && !((ShootAction) currentAction).isBaseUsed ( )) {
                            ((ShootAction) currentAction).setMustUseBase (true);
                            ((ShootAction) currentAction).addOptionalEffect (e);
                            options = new TargetOptions (gameModel.createTargetDetails (e), gameModel.findCompliantTargets (e, nickname), map, p.hasTargetingScope ());
                        } else if ((!e.isUsableBeforeBase() && ((ShootAction) currentAction).isBaseUsed ( )) || (e.isAlternativeMode ( ) && !((ShootAction) currentAction).isBaseUsed ( ))) {
                            ((ShootAction) currentAction).addOptionalEffect (e);
                            options = new TargetOptions (gameModel.createTargetDetails (e), gameModel.findCompliantTargets (e, nickname), map, p.hasTargetingScope ());
                        } } catch (NotEnoughAmmoException ex) {
                            //options is null
                        }
                    }
                }
            } else {
                lobby.sendToSpecific (nickname, new MessageForClient ("Error: you must use base effect."));
                options = setBaseEffect (nickname, map);
            }
        }
        if (options != null) {
            if (!((ShootAction)currentAction).getChosenWeapon ().getOptionalEffects ().isEmpty () && ((ShootAction)currentAction).getChosenWeapon ().getOptionalEffects ().get(0).isAlternativeMode ())
                ((ShootAction)currentAction).setEndAction(true);
            lobby.sendToSpecific (nickname, options);
        } else {
            lobby.sendToSpecific (nickname, new MessageForClient ("Error: you can't choose this effect/do not have enough ammo"));
            lobby.sendToSpecific (nickname, new WeaponModeOptions (gameModel.createWeaponEffects (((ShootAction)currentAction).getChosenWeapon ())));
        }
        gameModel.getGame ().updateCurrentAction (currentAction);
    }

    /**
     * Method to select the weapon's base effect for the shoot action.
     * @param nickname of current player
     * @param map is the list of SquareDetails with the map info for the client
     * @return TargetOptions is the type of target the client can choose with base effect
     */

    private TargetOptions setBaseEffect(String nickname, List<SquareDetails> map) {
        Player p = gameModel.getGame ().findByNickname (nickname);
        ((ShootAction) currentAction).addBaseEffect (((ShootAction) currentAction).getChosenWeapon ( ).getBaseEffect ( ));
        return new TargetOptions (gameModel.createTargetDetails (((ShootAction) currentAction).getChosenWeapon ( ).getBaseEffect ( )), gameModel.findCompliantTargets (((ShootAction) currentAction).getChosenWeapon ( ).getBaseEffect ( ), nickname), map, p.hasTargetingScope ());
    }

    /**
     * Method to set the chosen targets for the shoot action.
     * @param nickname of current player
     * @param targets are the chosen targets to hit
     * @param targetingScopeNickname is the nickname of the player that is hit by
     *                               Targeting Scope power up
     */

    public void setTargets(String nickname, List<AtomicTarget> targets, String targetingScopeNickname) {
        try {
            ((ShootAction) currentAction).setEffectTargets (targets, targetingScopeNickname);
            gameModel.getGame ().updateCurrentAction (currentAction);
            List<Player> tagbackUsers = gameModel.findPlayersEnabledToTagback();
            for (Player p : tagbackUsers)
                lobby.sendToSpecific (p.getPlayerName (), new TagbackRequest(nickname));
            if (! tagbackUsers.isEmpty ()) {
                try {
                    Thread.sleep (20000);
                } catch (InterruptedException e) {
                    Thread.currentThread ( ).interrupt ( );
                }
            }
            if (((ShootAction) currentAction).isEndAction ())
                checkNewTurn (nickname);
            else
                lobby.sendToSpecific (nickname, new WeaponModeOptions (gameModel.createWeaponEffects (((ShootAction) currentAction).getChosenWeapon ())));
        } catch (IllegalTargetException | IllegalUseOfPowerUpException | NotEnoughAmmoException e) {
            lobby.sendToSpecific (nickname, new MessageForClient (e.getMessage ()));
            checkNewTurn (nickname);
        }
    }

    /**
     * Method to use power up cards as bonus ammo.
     * @param nickname of current player
     * @param powerUpName of the power up chosen as bonus ammo
     */

    public void usePowerUpAsAmmo(String nickname, String powerUpName) {
        this.powerUpEffect = new PowerUpEffect(gameModel.getGame().findByNickname(nickname), gameModel.getGame().findByNickname(nickname).findPowerUp(powerUpName));
        this.powerUpEffect.usePupAmmo ();
        checkNewTurn (nickname);
    }

    /**
     * Method to ask the player in which way they want to use their power up.
     * @param nickname of current player
     * @param powerUpName of the power up chosen
     */

    public void choosePowerUpOption(String nickname, String powerUpName) {
        if (powerUpName.equals("Newton") || powerUpName.equals("Teleporter")) {
            this.powerUpEffect = new PowerUpEffect (gameModel.getGame ( ).findByNickname (nickname), gameModel.getGame ( ).findByNickname (nickname).findPowerUp (powerUpName));
            lobby.sendToSpecific (nickname, new PowerUpEffectOptions (powerUpName, gameModel.createSquareDetails ( ), gameModel.createPossiblePaths (nickname)));
        } else {
            lobby.sendToSpecific (nickname, new MessageForClient ("You can't use this power up right now."));
            checkNewTurn (nickname);
        }
    }

    /**
     * Method to apply the power up's effect.
     * @param nickname of current player
     * @param targetName is the nickname of the player hit by the power up
     * @param squareId is the id of the square chosen for movement effect
     */

    public void usePowerUpEffect(String nickname, String targetName, int squareId) {
        if (targetName != null) {
            try {
                this.powerUpEffect.useNewton (gameModel.getGame ( ).findByNickname (targetName), squareId);
            } catch (IllegalUseOfPowerUpException e) {
                lobby.sendToSpecific (nickname, new MessageForClient (e.getMessage ()));
            }
        } else {
            this.powerUpEffect.useTeleporter (squareId);
        }
        checkNewTurn (nickname);
    }

    /**
     * Method to apply Tagback Grenade effect if the player that has been hit wants to use
     * the power up.
     * @param toBeUsed true if the target wants to use it, false otherwise
     * @param user is the nickname of the target using the power up
     * @param target is the nickname of the player hit by the power up
     */

    public void useTagback(boolean toBeUsed, String user, String target) {
        if (toBeUsed) {
            Player userPlayer = gameModel.getGame ().findByNickname (user);
            Player targetPlayer = gameModel.getGame ().findByNickname (target);
            PowerUpEffect tagbackEffect = new PowerUpEffect (userPlayer, userPlayer.findPowerUp ("Tagback Grenade"));
            tagbackEffect.useTagbackGrenade (targetPlayer);
        }
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
        lobby.sendToAll (new MapInfo(SMALL, gameModel.getGame ().getMap ().getMapName ()));
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
        lobby.sendToAll (new TimeOutNotice (lobby.findPlayer(nickname)));
        playNewTurn ();
    }
}
