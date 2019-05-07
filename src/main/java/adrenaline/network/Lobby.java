package adrenaline.network;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_client.data_for_view.MapData;
import adrenaline.data.data_for_client.data_for_view.FirstPlayerSetUp;
import adrenaline.model.GameModel;
import adrenaline.timer.TimerCallBack;
import adrenaline.timer.TimerThread;

import java.util.ArrayList;

public class Lobby implements TimerCallBack {
    private MainServer server;
    private ArrayList<Account> players;
    private GameModel game;
    private Controller controller;
    private TimerThread timerThread;
    private boolean full;

    public Lobby(MainServer server) {
        this.server = server;
        this.players = new ArrayList<> ();
        this.controller = new Controller(server);
        this.full = false;
    }

    public void createGame() {
        String[] playerNames = new String[players.size ( )];
        int i = 0;
        for (Account a : players) {
            playerNames[i] = a.getNickName ( );
            i++;
        }
        this.game = new GameModel (playerNames);
        this.controller.setGameModel(game);
    }

    public Controller getController() {
        return this.controller;
    }

    public GameModel getGameModel() {
        return this.game;
    }

    public void checkReady() {
        if (this.players.size() > 2 && this.players.size() < 6) {
            if (isFull ()) {
                createGame ();
            } else {
                this.timerThread.startThread ();
            }
        }

    }

    private boolean checkFull() {
        if (this.players.size () == 5)
            full = true;
        return full;
    }

    public boolean isFull() {
        return checkFull ();
    }

    public void setPlayers(Account a) {
        checkFirst(a);
        this.players.add (a);
        checkReady();
    }

    private void checkFirst(Account a) {
        if (players.isEmpty ()) {
            FirstPlayerSetUp data = new FirstPlayerSetUp (true, a);
            data.sendToView ();
        } else {
            FirstPlayerSetUp data = new FirstPlayerSetUp (false, a);
            data.sendToView ();
        }
    }

    public void updateMapData() {
        for (Account a : players) {
            MapData data = this.game.updateMapData (a);
            data.sendToView ();
        }
    }

    @Override
    public void timerCallBack() {
        try {
            createGame ();
        } catch (Exception e) {
            System.err.println(e.getMessage ());
        }
    }

    @Override
    public void timerCallBack(String nickname) {
        throw new UnsupportedOperationException();
    }

}
