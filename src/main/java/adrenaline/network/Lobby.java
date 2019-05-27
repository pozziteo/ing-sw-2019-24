package adrenaline.network;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.data_for_network.MessageForClient;
import adrenaline.exceptions.GameStartedException;
import adrenaline.model.GameModel;
import adrenaline.utils.TimerCallBack;
import adrenaline.utils.TimerThread;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Lobby implements TimerCallBack {
    private int id;
    private MainServer server;
    private ArrayList<Account> players;
    private Controller controller;
    private TimerThread timerThread;
    private long timeout;
    private boolean gameStarted;
    private boolean full;
    private static final String PATH1 = "src" + File.separatorChar + "Resources" + File.separatorChar + "config.properties";

    public Lobby(int id, MainServer server) {
        this.id = id;
        this.server = server;
        this.players = new ArrayList<> ();
        this.controller = new Controller(this);
        this.timeout = readConfigFile("lobbyTimeout");
        this.timerThread = new TimerThread (this, timeout);
        this.gameStarted = false;
        this.full = false;
    }

    private void createGame() {
        timerThread.shutDownThread ();
        this.gameStarted = true;
        String[] playerNames = new String[players.size ()];
        int i = 0;
        for (Account a : players) {
            playerNames[i] = a.getNickName ( );
            i++;
        }
        this.controller.startController (new GameModel (playerNames));
    }

    public ArrayList<Account> getPlayers() {
        return this.players;
    }

    public Controller getController() {
        return this.controller;
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
                    sendMessageToAll ("Your lobby is full, the game will begin shortly...\n");
                    createGame ( );
                } else {
                    sendMessageToAll ("Starting countdown for the game...  (Current players: " + this.players.size () + ")\n");
                    if (players.size() == 3)
                        this.timerThread.startThread ();
                }
            } else {
                sendMessageToAll ("There's not enough participants, waiting for more... (Current players: " + this.players.size () + ")\n");
            }
        } else {
            throw new GameStartedException ("Error. The game has already started.\n");
        }
    }

    public synchronized void removeDisconnected(Account disconnected) {
        players.remove (disconnected);
        sendMessageToAll (disconnected.getNickName () + " disconnected...\n");
        if (gameStarted) {
            if (players.size() < 3) {
                controller.endGame();
            } else {
                controller.informOfDisconnection(disconnected.getNickName ());
            }
        } else {
            if (timerThread.isRunning ()) {
                timerThread.shutDownThread ();
            }
            try {
                checkReady ( );
            } catch(GameStartedException e) {
                System.out.print(e.getMessage ());
            }
        }
    }

    public void sendMessageToWaiting(String content) {
        if (players.size() > 1) {
            for (Account a : players.subList (0, players.size ()-1)) {
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
        sendMessageToWaiting (a.getNickName () + " joined the lobby...");
        checkReady();
    }

    /**
     * Method to read from the Configuration File
     * @param key is the name of the value you want to read
     * @return a long type
     */
    public long readConfigFile(String key) {
        try {
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream(PATH1);
            prop.load(in);
            int i = Integer.parseInt(prop.getProperty(key));
            return (long)i;
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return -1;
    }

    // *****************************************************************************************************************
    // CALLBACK
    // *****************************************************************************************************************

    /**
     * Callback function for game creation time out. If there's at least three players in this lobby
     * the timer starts and gets interrupted upon reaching 5 players in total, otherwise it continues
     * and after ending a new game gets created.
     */

    @Override
    public void timerCallBack() {
        try {
            createGame ();
        } catch (Exception e) {
            System.err.println(e.getMessage ());
        }
    }

    /**
     * Method that implements utils.TimerCallBack interface. It is not used in this class.
     * @param nickname of player
     */

    @Override
    public void timerCallBack(String nickname) {
        throw new UnsupportedOperationException();
    }

}
