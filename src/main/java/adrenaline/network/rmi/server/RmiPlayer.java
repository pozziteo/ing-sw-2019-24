package adrenaline.network.rmi.server;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.data_for_network.ClientSetUp;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.Account;
import adrenaline.network.MainServer;

/**
 * Class that instantiate a client connected with RMI on the server to maintain a reference to him and send him data
 * whene events occurs.
 */
public class RmiPlayer extends Account {

    /**
     * References the RMI client handler that manage the communication between client and server
     */
    private transient RmiServerClientHandler handler;

    /**
     * Instantiate a new RMI client on the server, saving the nickname of the client, a refrence to the client handlers
     * and a reference to the main server
     * @param server is a reference to the main server
     * @param nickname is the nickname of the client to instantiate
     * @param handler is the object that manages communication between client and server in RMI
     */
    public RmiPlayer(MainServer server, String nickname, RmiServerClientHandler handler) {
        super(nickname, server);
        this.handler = handler;
    }

    /**
     * Performs first operations to log the client on server and add him in a lobby
     */
    protected void setUpAccount() {
        setOnline(true);
        logClient();
    }

    /**
     * Asks the client to send authentication parameters, that is his nickname
     */
    protected void setUpClient() {
        this.sendData(new ClientSetUp (this));
    }

    /**
     * Reflection method to pass data coming from client to the main server
     */
    public void receiveData(DataForServer data) {
        super.getServer().receiveData(this, data);
    }

    /**
     * Overrides superclass method to set a client online or offline when an event occurs
     * @param value is true if the client is logging in, false if the client logged out
     */
    @Override
    protected final void setOnline(boolean value) {
        super.setOnline(value);
    }

    /**
     * Calls super method to log the client in
     */
    @Override
    public final void logClient() {
        super.logClient();
    }

    /**
     * Calls method on the client handler to send to client a pack of data
     * @param data is the pack of data to send
     */
    @Override
    public void sendData(DataForClient data) {
        handler.sendDataTo(this, data);
    }

}


