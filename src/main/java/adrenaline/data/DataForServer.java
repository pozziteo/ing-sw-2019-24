package adrenaline.data;

//TODO javadoc

import adrenaline.network.Account;

import java.io.Serializable;

public abstract class DataForServer implements Serializable {
    private Account account;

    public DataForServer() {
    }
}
