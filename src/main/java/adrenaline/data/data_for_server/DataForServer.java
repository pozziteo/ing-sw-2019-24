package adrenaline.data.data_for_server;

import adrenaline.network.Account;
import adrenaline.network.MainServer;

import java.io.Serializable;
import java.util.List;

/**
 * Class that represents data that has to be sent to the server.
 * Implements command pattern.
 */

public abstract class DataForServer implements Serializable {
    private static final long serialVersionUID = -6009348198210309658L;
    private String nickname;

    public DataForServer(String nickname) {
        this.nickname = nickname;
    }

    public Account findAccount(List<Account> accounts) {
        for (Account a : accounts) {
            if (a.getNickName ().equals(nickname)) {
                return a;
            }
        }
        return null;
    }

    public String getNickname() {
        return this.nickname;
    }

    /**
     * Generic method used to update data in the model.
     * Implements command pattern.
     */

    public void updateModel() {
        //implemented by subclasses
    }

    public void updateServer(MainServer server) {
        //implemented by subclasses
    }
}
