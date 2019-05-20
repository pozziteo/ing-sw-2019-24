package adrenaline.network.rmi.server;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.data_for_network.ClientSetUp;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.Account;
import adrenaline.network.MainServer;

public class RmiPlayer extends Account {

    private transient RmiServerClientHandler handler;

    public RmiPlayer(MainServer server, String nickname, RmiServerClientHandler handler) {
        super(nickname, server);
        this.handler = handler;
    }

    protected void setUpAccount() {
        setOnline(true);
        logClient();
    }

    protected void setUpClient() {
        this.sendData(new ClientSetUp (this));
    }

    public void receiveData(DataForServer data) {
        super.getServer().receiveData(this, data);
    }

    @Override
    protected final void setOnline(boolean value) {
        super.setOnline(value);
    }

    @Override
    public final void logClient() {
        super.logClient();
    }

    @Override
    public void sendData(DataForClient data) {
        handler.sendDataTo(this, data);
    }

}


