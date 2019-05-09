package adrenaline.network;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.data_for_game.MapData;
import adrenaline.data.data_for_client.data_for_network.MessageForClient;
import adrenaline.exceptions.GameStartedException;
import adrenaline.model.GameModel;
import adrenaline.misc.TimerCallBack;
import adrenaline.misc.TimerThread;

import java.util.ArrayList;

public class Lobby implements TimerCallBack {
    private int id;
    private MainServer server;
    private ArrayList<Account> players;
    private GameModel game;
    private Controller controller;
    private TimerThread timerThread;
    private long timeout;
    private boolean gameStarted;
    private boolean full;

    public Lobby(int id, MainServer server) {
        this.id = id;
        this.server = server;
        this.players = new ArrayList<> ();
        this.controller = new Controller(this);
        this.timeout = (long) 25 * 1000;
        this.timerThread = new TimerThread (this, timeout);
        this.gameStarted = false;
        this.full = false;
    }

    private void createGame() {
        try {
            System.out.println("Lobby " + id + ": Timer stopped\n");
            timerThread.shutDownThread ( );
            if (thereIsEnoughPlayers ( )) {
                this.gameStarted = true;
                String[] playerNames = new String[players.size ( )];
                int i = 0;
                for (Account a : players) {
                    playerNames[i] = a.getNickName ( );
                    i++;
                }
                this.game = new GameModel (playerNames);
                this.controller.startController (game);
            } else {
                System.err.println ("Error: Someone left during the wait. (Lobby: " + id + ")\n");
                sendMessageToAll ("Disconnection error. There's not enough players.\n");
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage ());
        }
    }

    public ArrayList<Account> getPlayers() {
        return this.players;
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

    public Account findPlayer(String nickname) {
        return server.findClient (nickname);
    }

    public void sendToSpecific(String nickname, DataForClient data) {
        for (Account player : players) {
            if (player.getNickName ().equals(nickname)) {
                data.setAccount (player);
                data.sendToView ();
                break;
            }
        }
    }

    public boolean thereIsEnoughPlayers() {
        if (this.players.size() > 2 && this.players.size() < 6)
            return true;
        return false;
    }

    public synchronized void checkReady() throws GameStartedException {
        if (!gameStarted) {
            if (thereIsEnoughPlayers ()) {
                if (isFull ()) {
                    sendMessageToWaiting ("Your lobby is full, the game will begin shortly\n");
                    createGame ( );
                } else {
                    sendMessageToWaiting ("Waiting for more players... (Current players: " + this.players.size () + ")\n");
                    this.timerThread.startThread ();
                    System.out.println ("Lobby " + id + ": Timer started\n");
                }
            } else {
                sendMessageToWaiting ("Your lobby does not have enough players, waiting for more... (Current players: " + this.players.size () + ")\n");
            }
        } else {
            throw new GameStartedException ("Error. The game has already started.\n");
        }
    }

    public void removeDisconnected(Account disconnected) {
        if (gameStarted) {
            //TODO
        } else {
            players.remove (disconnected);
            sendMessageToAll (disconnected.getNickName () + " left the lobby...\n");
        }
    }

    private void sendMessageToWaiting(String content) {
        for (Account a : players.subList (0, players.size ()-1)) {
            MessageForClient message = new MessageForClient (a, content);
            message.sendToView ();
        }
    }

    private void sendMessageToAll(String content) {
        for (Account a : players) {
            MessageForClient message = new MessageForClient (a, content);
            message.sendToView ();
        }
    }

    public boolean isFull() {
        if (this.players.size () == 5) {
            full = true;
        } else {
            full = false;
        }
        return full;
    }

    public synchronized void setPlayers(Account a) throws GameStartedException {
        this.players.add (a);
        checkReady();
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
            System.err.println("Lobby " + id + ": timer is up... Creating new game\n");
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
