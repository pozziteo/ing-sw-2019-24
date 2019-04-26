package data;

import network.visitors.Account;

import java.io.Serializable;

//TODO javadoc

public abstract class DataForClient implements Serializable {
    private Account account;

    public DataForClient(Account account) {
        this.account = account;
    }

    public void sendToView() {
        if (account.isOnline()) {
            try {
                account.sendData(this);
            } catch (Exception e) {
                System.out.println (e);
            }
        }
    }
}
