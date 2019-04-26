package network;

import data.DataForClient;
import model.GameModel;

import java.util.ArrayList;

//TODO javadoc

public abstract class Account {
    private String nickname;
    private boolean online = false;
    private GameModel currentGame;
    private ArrayList<GameModel> gameHistory;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickName() {
        return this.nickname;
    }

    public void setOnline() {
        this.online = true;
    }

    public boolean isOnline() {
        return this.online;
    }

    public void sendData(DataForClient data) {
        //implemented by subclasses
    }
}
