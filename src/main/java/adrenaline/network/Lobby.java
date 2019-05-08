package adrenaline.network;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_client.data_for_view.LobbyStatus;
import adrenaline.data.data_for_client.data_for_view.MapData;
import adrenaline.data.data_for_client.data_for_view.FirstPlayerSetUp;
import adrenaline.data.data_for_client.data_for_view.MessageForClient;
import adrenaline.model.GameModel;
import adrenaline.misc.TimerCallBack;
import adrenaline.misc.TimerThread;

import java.util.ArrayList;

public class Lobby implements TimerCallBack {
    private MainServer server;
    private ArrayList<Account> players;
    private GameModel game;
    private Controller controller;
    private TimerThread timerThread;
    private long timeout;
    private boolean gameStarted;
    private boolean full;

    public Lobby(MainServer server) {
        this.server = server;
        this.players = new ArrayList<> ();
        this.controller = new Controller(server);
        this.timeout = (long) 10 * 1000;
        this.timerThread = new TimerThread (this, timeout);
        this.gameStarted = false;
        this.full = false;
    }

    private void createGame() {
        this.gameStarted = true;
        String[] playerNames = new String[players.size ( )];
        int i = 0;
        for (Account a : players) {
            playerNames[i] = a.getNickName ( );
            i++;
        }
        this.game = new GameModel (playerNames);
        this.controller.startController (game);
    }

    public Controller getController() {
        return this.controller;
    }

    public GameModel getGameModel() {
        return this.game;
    }

    public boolean isGameStarted() {
        return this.gameStarted;
    }

    public synchronized void checkReady() {
        if (this.players.size() > 2 && this.players.size() < 6) {
            if (isFull ()) {
                sendLobbyStatusToAll (true, "Your lobby is full, the game will begin shortly\n");
                createGame ( );
            } else {
                sendLobbyStatusToAll (false, "Waiting for more players... (Current players: " + this.players.size () + ")\n");
                this.timerThread.startThread ();
            }
        } else {
            sendLobbyStatusToAll (false, "Your lobby does not have enough players, waiting for more... (Current players: " + this.players.size () + ")\n");
        }
    }

    public void removeDisconnected(Account disconnected) {
        if (gameStarted) {
            //TODO
        } else {
            players.remove (disconnected);
            for (Account connected : players) {
                MessageForClient message = new MessageForClient (connected, disconnected.getNickName () + " left the lobby...\n");
                message.sendToView ();
            }
        }
        checkReady ();
    }

    private void sendLobbyStatusToAll(boolean value, String message) {
        for (Account a : players) {
            LobbyStatus data = new LobbyStatus (a, value, message);
            data.sendToView ();
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

    public synchronized void setPlayers(Account a) {
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
            System.err.println("Lobby timer is up... Creating new game\n");
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
