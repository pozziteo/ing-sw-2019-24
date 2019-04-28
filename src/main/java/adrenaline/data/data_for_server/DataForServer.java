package adrenaline.data.data_for_server;

import adrenaline.network.Account;

import java.io.Serializable;

/**
 * Class that represents data that has to be sent to the server.
 * Implements command pattern.
 */

public abstract class DataForServer implements Serializable {
    private Account account;

    public void setAccount(Account a) {
        this.account = a;
    }

    public Account getAccount() {
        return this.account;
    }

    /**
     * Generic method used to update data in the model.
     * Implements command pattern.
     */

    public void updateModel() {
        //implemented by subclasses
    }
}
