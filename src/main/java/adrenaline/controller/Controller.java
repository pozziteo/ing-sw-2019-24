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
import java.util.ArrayList;
import java.util.List;

public class Controller implements TimerCallBack {
    private Lobby lobby;
    private GameModel gameModel;
    private TimerThread timer;
    private ArrayList<Player> dummyPlayers;
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

    public GameModel getGameModel() {
        return this.gameModel;
    }

    public Lobby getLobby() {
        return this.lobby;
    }

    public void receiveData(DataForController data) {
        Runnable thread = () -> {
            Thread.currentThread ().setName ("Controller Receiver Thread");
            System.out.println("Received " + data.getClass ().getSimpleName () + " in lobby #" + lobby.getId());
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
        MapSetUp data = new MapSetUp (firstPlayer.getPlayerName ());
        for (Player p : gameModel.getGame ().getPlayers ()) {
            lobby.sendToSpecific (p.getPlayerName (), data);
        }
    }

    public void initializeMap(String filepath) {
        gameModel.getGame ().setArena (filepath);
        lobby.sendToAll (new MapInfo(filepath, gameModel.getGame ().getMap ().getMapName ()));
        spawnPointSetUp ();
    }

    private void spawnPointSetUp() {
        timer.shutDownThread ();
        for (Player p : gameModel.getGame ().getPlayers ()) {
            SpawnPointSetUp data = new SpawnPointSetUp (gameModel.createPowerUpDetails (p));
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
        if (!gameModel.getGame ().isStartGame () && checkPlayersReady()) {
            System.out.println("All players have spawned...\n");
            lobby.sendMessageToAll("All players have spawned.\n");
            gameModel.getGame ().startGame();
            playNewTurn ();
        } else if (gameModel.getGame ().isStartGame ()) {
            lobby.sendToSpecific (nickname, new Turn (nickname));
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
                options = new ShootOptions(weaponDetails);
                lobby.sendToSpecific (nickname, options);
                break;
            case "power up":
                if (! gameModel.getGame ().findByNickname (nickname).getOwnedPowerUps ().isEmpty ()) {
                    options = new PowerUpOptions(gameModel.createPowerUpDetails (gameModel.getGame ().findByNickname (nickname)));
                    lobby.sendToSpecific (nickname, options);
                }
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
        if (gameModel.getGame ().findByNickname (nickname).getOwnedWeapons ().remove (weapon) || gameModel.getGame ().findByNickname (nickname).getBoard ().getUnloadedWeapons ().remove (weapon)) {
            gameModel.getGame ().replaceWeapon(gameModel.getGame ().findByNickname (nickname), weapon);
        }
        checkNewTurn (nickname);
    }

    private boolean isLastAction() {
        return (gameModel.getGame ().getCurrentTurnActionNumber () == 2);
    }

    public void sendModeOptions(String nickname, String weaponName) {
        Weapon weapon = gameModel.getGame ().findByNickname (nickname).findLoadedWeapon (weaponName);
        ((ShootAction) currentAction).setChosenWeapon (weapon);
        gameModel.getGame ().updateCurrentAction (currentAction);
        if (weapon != null) {
            lobby.sendToSpecific (nickname, new WeaponModeOptions (gameModel.createWeaponEffects (weapon)));
        }
    }

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
                        } else if (!e.isUsableBeforeBase ( ) && ((ShootAction) currentAction).isBaseUsed ( ) || (e.isAlternativeMode ( ) && !((ShootAction) currentAction).isBaseUsed ( ))) {
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
            lobby.sendToSpecific (nickname, new MessageForClient ("Error: you can't choose this effect."));
            lobby.sendToSpecific (nickname, new WeaponModeOptions (gameModel.createWeaponEffects (((ShootAction)currentAction).getChosenWeapon ())));
        }
        gameModel.getGame ().updateCurrentAction (currentAction);
    }

    private TargetOptions setBaseEffect(String nickname, List<SquareDetails> map) {
        Player p = gameModel.getGame ().findByNickname (nickname);
        ((ShootAction) currentAction).addBaseEffect (((ShootAction) currentAction).getChosenWeapon ( ).getBaseEffect ( ));
        return new TargetOptions (gameModel.createTargetDetails (((ShootAction) currentAction).getChosenWeapon ( ).getBaseEffect ( )), gameModel.findCompliantTargets (((ShootAction) currentAction).getChosenWeapon ( ).getBaseEffect ( ), nickname), map, p.hasTargetingScope ());
    }

    public void setTargets(String nickname, List<AtomicTarget> targets, String targetingScopeNickname) {
        try {
            ((ShootAction) currentAction).setEffectTargets (targets, targetingScopeNickname);
            gameModel.getGame ().updateCurrentAction (currentAction);
            if (((ShootAction) currentAction).isEndAction ()) {
                List<Player> tagbackUsers = gameModel.findPlayersEnabledToTagback();
                if (! tagbackUsers.isEmpty ()) {
                    for (Player p : tagbackUsers)
                        lobby.sendToSpecific (p.getPlayerName (), new TagbackRequest());
                    try {
                        Thread.sleep (20000);
                    } catch (InterruptedException e) {
                        Thread.currentThread ().interrupt ();
                    }
                    checkNewTurn (nickname);
                } else
                    checkNewTurn (nickname);
            }
            else
                lobby.sendToSpecific (nickname, new WeaponModeOptions (gameModel.createWeaponEffects (((ShootAction) currentAction).getChosenWeapon ())));
        } catch (IllegalTargetException | IllegalUseOfPowerUpException | NotEnoughAmmoException e) {
            lobby.sendToSpecific (nickname, new MessageForClient (e.getMessage ()));
            checkNewTurn (nickname);
        }
    }

    public void usePowerUpAsAmmo(String nickname, String powerUpName) {
        this.powerUpEffect = new PowerUpEffect(gameModel.getGame().findByNickname(nickname), gameModel.getGame().findByNickname(nickname).findPowerUp(powerUpName));
        this.powerUpEffect.usePupAmmo ();
        checkNewTurn (nickname);
    }

    public void choosePowerUpOption(String nickname, String powerUpName) {
        if (powerUpName.equals("Newton") || powerUpName.equals("Teleporter")) {
            this.powerUpEffect = new PowerUpEffect (gameModel.getGame ( ).findByNickname (nickname), gameModel.getGame ( ).findByNickname (nickname).findPowerUp (powerUpName));
            lobby.sendToSpecific (nickname, new PowerUpEffectOptions (powerUpName, gameModel.createSquareDetails ( )));
        } else {
            lobby.sendToSpecific (nickname, new MessageForClient ("You can't use this power up right now."));
            checkNewTurn (nickname);
        }
    }

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
