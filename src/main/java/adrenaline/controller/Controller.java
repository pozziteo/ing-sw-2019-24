package adrenaline.controller;

import adrenaline.data.data_for_client.data_for_game.*;
import adrenaline.data.data_for_client.data_for_network.MessageForClient;
import adrenaline.data.data_for_server.data_for_game.DataForController;
import adrenaline.model.GameModel;
import adrenaline.model.player.Action;
import adrenaline.model.player.Player;
import adrenaline.network.Lobby;
import adrenaline.misc.TimerCallBack;
import adrenaline.misc.TimerThread;

import java.io.File;

public class Controller implements TimerCallBack {
    private Lobby lobby;
    private GameModel gameModel;
    private long timeout;
    private TimerThread timer;

    //path for default map
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";

    public Controller(Lobby lobby) {
        this.lobby = lobby;
        this.timeout = (long) 10 * 1000;
        this.timer = new TimerThread (this, timeout);
    }

    public GameModel getGameModel() {
        return this.gameModel;
    }

    public void receiveData(DataForController data) {
        data.updateGame (this);
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

    public void spawnPointSetUp() {
        for (Player p : gameModel.getGame ().getPlayers ()) {
            InitialSpawnPointSetUp data = new InitialSpawnPointSetUp(p.getOwnedPowerUps ());
            lobby.sendToSpecific (p.getPlayerName (), data);
        }
    }

    public void setSpawnPoint(String nickname, String color){
        for (Player p : gameModel.getGame().getPlayers()){
            if(p.getPlayerName().equals(nickname)){
                p.chooseSpawnPoint(color);
            }
        }
    }

    public void playTurn(int n) {
        if (! gameModel.getGame ().isEndGame()) {
            if (! gameModel.getGame ().isFinalFrenzy ()) {
                int indexOfLast = gameModel.getGame ().getPlayers ().size ()-1;
                int currentTurn = gameModel.getGame ( ).getCurrentTurn ( );
                String currentPlayer = lobby.getPlayers ().get (indexOfLast - currentTurn).getNickName ();
                lobby.sendToSpecific (currentPlayer, new Turn(true, gameModel.getGame ().getMap ()));
                lobby.sendToAllNonCurrent (currentPlayer, new Turn(false, gameModel.getGame ().getMap ()));
                timer.startThread (currentPlayer);
                gameModel.getGame ( ).incrementTurn ( );
            } else {
                //TODO
            }
        } else {
            //TODO
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
    }
}
