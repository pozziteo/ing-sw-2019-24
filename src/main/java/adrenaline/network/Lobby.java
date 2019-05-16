package adrenaline.network;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_client.DataForClient;
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
        timerThread.shutDownThread ();
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
            System.err.println ("Error: Someone left during the set up. (Lobby: " + id + ")\n");
            sendMessageToAll ("Disconnection error. There's not enough players.\n");
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

    public void sendToAllNonCurrent(String current, DataForClient data) {
        for (Account a : players) {
            if (! a.getNickName ().equals (current)) {
                data.setAccount (a);
                data.sendToView ();
            }
        }
    }

    private boolean thereIsEnoughPlayers() {
        return (this.players.size() > 2 && this.players.size() < 6);
    }

    private synchronized void checkReady() throws GameStartedException {
        if (!gameStarted) {
            if (thereIsEnoughPlayers ()) {
                if (isFull ()) {
                    sendMessageToWaiting ("Your lobby is full, the game will begin shortly...\n");
                    createGame ( );
                } else {
                    sendMessageToWaiting ("A new player joined your lobby. Starting countdown for the game...  (Current players: " + this.players.size () + ")\n");
                    if (players.size() == 3)
                        this.timerThread.startThread ();
                }
            } else {
                sendMessageToWaiting ("A new player joined. There's not enough participants, waiting for more... (Current players: " + this.players.size () + ")\n");
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

    public void sendMessageToWaiting(String content) {
        if (players.size() != 1) {
            for (Account a : players.subList (0, players.size ())) {
                MessageForClient message = new MessageForClient (a, content);
                message.sendToView ();
            }
        }
    }

    public void sendMessageToAll(String content) {
        for (Account a : players) {
            MessageForClient message = new MessageForClient (a, content);
            message.sendToView ();
        }
    }

    public boolean isFull() {
        setFull ();
        return full;
    }

    private void setFull() {
        if (this.players.size () == 5) {
            full = true;
        } else {
            full = false;
        }
    }

    public synchronized void addPlayer(Account a) throws GameStartedException {
        this.players.add (a);
        checkReady();
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
