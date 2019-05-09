package adrenaline.controller;

import adrenaline.data.data_for_client.data_for_game.InitialSpawnPointSetUp;
import adrenaline.data.data_for_client.data_for_game.MapSetUp;
import adrenaline.data.data_for_client.data_for_network.MessageForClient;
import adrenaline.data.data_for_server.data_for_game.DataForController;
import adrenaline.model.GameModel;
import adrenaline.model.player.Player;
import adrenaline.network.Lobby;
import adrenaline.misc.TimerCallBack;
import adrenaline.misc.TimerThread;

public class Controller implements TimerCallBack {
    private Lobby lobby;
    private GameModel gameModel;
    private long timeout;
    private TimerThread timer;

    public Controller(Lobby lobby) {
        this.lobby = lobby;
        this.timeout = (long) 120 * 1000;
        this.timer = new TimerThread (this, timeout);
    }

    public void startController(GameModel model) {
        this.gameModel = model;
        this.timer = new TimerThread (this, timeout);
        mapSetUp ();
    }

    private void mapSetUp() {
        Player firstPlayer = gameModel.getGame ().getPlayers ().get (0);
        MapSetUp data = new MapSetUp ();
        lobby.sendToSpecific (firstPlayer.getPlayerName (), data);
        int numberOfPlayers = gameModel.getGame ().getPlayers ().size ();
        for (Player p : gameModel.getGame ().getPlayers ().subList (1, numberOfPlayers-1)) {
            lobby.sendToSpecific (p.getPlayerName (), new MessageForClient ("The first player in your lobby is choosing the arena...\n"));
        }
    }

    public void spawnPointSetUp() {
        for (Player p : gameModel.getGame ().getPlayers ()) {
            InitialSpawnPointSetUp data = new InitialSpawnPointSetUp(p.getOwnedPowerUps ());
            lobby.sendToSpecific (p.getPlayerName (), data);
        }
    }

    public GameModel getGameModel() {
        return this.gameModel;
    }

    public void receiveData(DataForController data) {
        data.updateGame (this);
    }

    @Override
    public void timerCallBack() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void timerCallBack(String nickname) {
        MessageForClient message = new MessageForClient (lobby.findPlayer(nickname), "Time is up. You took too long to make a choice.");
        message.sendToView ();
    }
}
