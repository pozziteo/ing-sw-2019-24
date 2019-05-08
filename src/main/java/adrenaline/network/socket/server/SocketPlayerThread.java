package adrenaline.network.socket.server;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.data_for_view.ClientSetUp;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.MainServer;
import adrenaline.network.Account;

import java.io.*;
import java.net.Socket;

/**
 * Class that implements the thread (server side) for every single client connected.
 */

public class SocketPlayerThread extends Account implements Runnable {
    private transient Socket socket;
    private transient ObjectInputStream in;
    private transient ObjectOutputStream out;

    public SocketPlayerThread(MainServer server, Socket s, String nickname) {
        super(nickname, server);
        this.socket = s;
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out.flush();
        } catch (IOException exc) {
            super.setOnline (false);
        }
    }

    /**
     * Implementation of run() method of Runnable interface
     */

    @Override
    public void run() {
        try {
            setUpAccount ();
            setUpClient ();
            while (socket.isConnected ()) {
                DataForServer receivedData = (DataForServer) in.readObject ();
                super.getServer().receiveData(this, receivedData);
            }
        } catch (Exception e) {
            System.err.println (e.getMessage());
            super.setOnline (false);
        } finally {
            super.setOnline (false);
        }
        closeThread ();
    }

    /**
     * Method to forward data from server to client
     * @param data meant for the client
     */

    @Override
    public void sendData(DataForClient data) {
        try {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method to shut down this thread
     */

    private void closeThread() {
        try {
            disconnectClient ();
            socket.close ( );
            in.close();
            out.close();
        } catch(IOException e) {
            System.err.println (e.getMessage());
        }
    }

    private void setUpAccount() {
        super.setOnline (true);
        super.logClient ();
    }

    private void setUpClient() {
        this.sendData(new ClientSetUp (this));
    }

    private void disconnectClient() {
        System.out.println (super.getNickName () + " disconnected");
        super.setOnline (false);
        super.getServer ().notifyDisconnection (super.getNickName ());
    }

}
