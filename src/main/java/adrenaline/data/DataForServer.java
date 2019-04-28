package adrenaline.data;

//TODO javadoc

import adrenaline.network.Account;
import adrenaline.network.MainServer;

import java.io.Serializable;

public abstract class DataForServer implements Serializable {
    private MainServer server;

    public DataForServer() {
    }

    public MainServer getServer() {
        return this.server;
    }
}
