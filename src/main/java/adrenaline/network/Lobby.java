package adrenaline.network;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.data_for_network.MessageForClient;
import adrenaline.exceptions.ClosedLobbyException;
import adrenaline.exceptions.GameStartedException;
import adrenaline.model.GameModel;
import adrenaline.utils.ConfigFileReader;
import adrenaline.utils.TimerCallBack;
import adrenaline.utils.TimerThread;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to gather all the clients waiting for other players before starting a new game.
 */

public class Lobby implements TimerCallBack {
    private int id;
    private MainServer server;
    private ArrayList<Account> players;
    private ArrayList<String> disconnected;
    private Controller controller;
    private TimerThread timerThread;
    private long timeout;
    private boolean gameStarted;
    private boolean full;

    public Lobby(int id, MainServer server) {
        this.id = id;
        this.server = server;
        this.players = new ArrayList<> ();
        this.disconnected = new ArrayList<> ();
        this.controller = new Controller(this);
        this.timeout = (long) ConfigFileReader.readConfigFile("lobbyTimeout");
        this.timerThread = new TimerThread (this, timeout);
        this.gameStarted = false;
        this.full = false;
    }

    /**
     * Method that creates a new game
     */
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

    /**
     * Getter method for id of the lobby
     * @return id
     */

    public int getId() {
        return this.id;
    }

    /**
     * Getter method
     * @return the list of players
     */
    public List<Account> getPlayers() {
        return this.players;
    }

    /**
     * Getter method for the list of disconnected client names
     * @return list of names
     */

    public List<String> getDisconnected() {
        return this.disconnected;
    }

    /**
     * Getter method
     * @return the controller
     */
    public Controller getController() {
        return this.controller;
    }

    /**
     * @return true if the Game has started
     */
    public boolean isGameStarted() {
        return this.gameStarted;
    }

    /**
     * @param nickname is the player's nickname
     * @return the nickname of the client
     */
    public Account findPlayer(String nickname) {
        return server.findClient (nickname);
    }

    /**
     * Method that sends data to a specific player
     * @param nickname is the player's nickname
     * @param data is the data
     */
    public void sendToSpecific(String nickname, DataForClient data) {
        for (Account player : players) {
            if (player.getNickName ().equals(nickname)) {
                data.setAccount (player);
                data.sendToView ();
                break;
            }
        }
    }

    /**
     * Method that sends data to every waiting players
     * @param current is the current player
     * @param data is the data
     */
    public void sendToAllNonCurrent(String current, DataForClient data) {
        for (Account a : players) {
            if (! a.getNickName ().equals (current)) {
                data.setAccount (a);
                data.sendToView ();
            }
        }
    }

    /**
     * @return true if the number of players is between 3 and 5
     */
    private boolean thereIsEnoughPlayers() {
        return (this.players.size() > 2 && this.players.size() < 6);
    }

    /**
     * Method that checks if the number of players is enough to begin the timer
     * @throws GameStartedException if the game has already started
     */
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

    /**
     * Method that removes from the players list the one who disconnected
     * @param disconnected is the account of the player
     */
    public synchronized void removeDisconnected(Account disconnected) throws ClosedLobbyException {
        players.remove (disconnected);
        this.disconnected.add(disconnected.getNickName ());
        sendMessageToAll (disconnected.getNickName () + " disconnected...\n");
        if (gameStarted) {
            if (players.size() < 3 && !controller.getGameModel ().getGame ().isEndGame ()) {
                controller.endGameBecauseOfDisconnection ();
                throw new ClosedLobbyException ();
            } else if (players.size () > 2) {
                controller.informOfDisconnection(disconnected.getNickName ());
            }
        } else {
            if (!players.isEmpty ()) {
                if (timerThread.isRunning ( )) {
                    timerThread.shutDownThread ( );
                }
                try {
                    checkReady ( );
                } catch (GameStartedException e) {
                    System.out.print (e.getMessage ( ));
                }
            } else
                throw new ClosedLobbyException ();
        }
    }

    /**
     * Method that sends a message to every player waiting
     * @param content is the message
     */
    public void sendMessageToWaiting(String content) {
        if (players.size() > 1) {
            for (Account a : players.subList (0, players.size ()-1)) {
                MessageForClient message = new MessageForClient (a, content);
                message.sendToView ();
            }
        }
    }

    /**
     * Method that sends a message to every player
     * @param content is the message
     */
    public void sendMessageToAll(String content) {
        for (Account a : players) {
            MessageForClient message = new MessageForClient (a, content);
            message.sendToView ();
        }
    }

    /**
     * Method to send some data to all the clients in this lobby
     * @param data to send
     */

    public void sendToAll(DataForClient data) {
        for (Account a : players) {
            data.setAccount(a);
            data.sendToView ();
        }
    }

    /**
     * @return true if the lobby is full
     */
    public boolean isFull() {
        setFull ();
        return full;
    }

    /**
     * Setter method for "full" boolean
     */
    private void setFull() {
        if (this.players.size () == 5) {
            full = true;
        } else {
            full = false;
        }
    }

    /**
     * Method that adds one player to the lobby
     * @param a is the account of the player
     * @throws GameStartedException
     */
    public synchronized void addPlayer(Account a) throws GameStartedException {
        this.players.add (a);
        sendMessageToWaiting (a.getNickName () + " joined the lobby...");
        checkReady();
    }

    /**
     * Method to add a player that disconnected back into the game
     * @param a to add back
     */

    public void addPlayerBack(Account a) {
        this.players.add(a);
        this.disconnected.remove (a.getNickName ());
        controller.informOfReconnection (a.getNickName ());
    }

    /**
     * Getter method to get the mainserver
     * @return the mainserver
     */
    public MainServer getServer(){
        return this.server;
    }

    /**
     * Method to destroy the lobby after the game ended
     */

    public void destroy() {
        server.removeLobby(this);
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
