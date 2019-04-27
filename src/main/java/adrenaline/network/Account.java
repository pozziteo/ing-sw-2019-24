package adrenaline.network;

import adrenaline.data.DataForClient;
import adrenaline.model.GameModel;

import java.io.Serializable;
import java.util.ArrayList;

//TODO javadoc

public abstract class Account implements Serializable {
    private String nickname;
    private boolean online = false;
    private GameModel currentGame;
    private ArrayList<GameModel> gameHistory;



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

    /**
     * SETTER Method to set the online status = true
     */
    public void setOnline(boolean value) {
        this.online = value;
    }

    /**
     * Method to know if the status is online
     * @return a boolean
     */
    public boolean isOnline() {
        return this.online;
    }

    public void sendData(DataForClient data) {
        //implemented by subclasses
    }
}
