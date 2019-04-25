package data;

import network.visitors.Account;

import java.io.Serializable;

public abstract class DataForClient implements Serializable {
    private Account account;
    private int gameID;

    public DataForClient(Account account, int gameID) {
        this.account = account;
        this.gameID = gameID;
    }

    public void sendToView() {
        try {
            account.sendData(this);
        } catch (Exception e) {
            System.out.println (e);
        }
    }

}
