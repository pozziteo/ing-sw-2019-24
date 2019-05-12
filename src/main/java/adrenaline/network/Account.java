package adrenaline.network;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.exceptions.GameStartedException;
import adrenaline.model.GameModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores the data of a client and differentiates it from other players
 * based on the account's unique nickname.
 */

public class Account implements Serializable {
    private static final long serialVersionUID = -663080251766657000L;

    private String nickname;
    private transient boolean online;
    private transient MainServer server;
    private transient Lobby currentLobby;
    private List<GameModel> gameHistory;

    public Account(String nick, MainServer server) {
        this.nickname = nick;
        this.server = server;
        this.online = false;
        this.currentLobby = null;
        this.gameHistory = new ArrayList<> ();
    }

    /**
     * SETTER Method to set the player's nickname
     * @param nickname is the nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    /**
     * GETTER Method
     * @return the player's nickname
     */
    public String getNickName() {
        return this.nickname;
    }

    public MainServer getServer() {
        return this.server;
    }

    public void setServer(MainServer server) {
        this.server = server;
    }

    /**
     * SETTER Method to set the online status = true
     */
    protected void setOnline(boolean value) {
        this.online = value;
    }

    /**
     * Method to know if the status is online
     * @return a boolean
     */
    public boolean isOnline() {
        return this.online;
    }

    /**
     * Getter to obtain the lobby this account is currently in
     * @return lobby
     */

    public Lobby getCurrentLobby() {
        return this.currentLobby;
    }

    public void setCurrentLobby(Lobby lobby) throws GameStartedException {
        this.currentLobby = lobby;
        addToLobby (lobby);
    }

    public List<GameModel> getGameHistory() {
        return this.gameHistory;
    }

    public void setGameHistory(List<GameModel> gameHistory) {
        this.gameHistory = gameHistory;
    }

    public void logClient() {
        server.logClient (this);
    }

    /**
     * Method to add this account to the first open lobby in the main server
     */

    public void addToLobby(Lobby lobby) throws GameStartedException {
        if (!lobby.isFull()) {
            lobby.addPlayer (this);
        }
    }

    /**
     * Method to add a game to the list of stored games
     * @param game to save
     */

    public void storeGame(GameModel game) {
        this.gameHistory.add (game);
    }

    /**
     * Method to send data to a client
     * @param data to send
     */

    public void sendData(DataForClient data) {
        //implemented by subclasses
    }
}
