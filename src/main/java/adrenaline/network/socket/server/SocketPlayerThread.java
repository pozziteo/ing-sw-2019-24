package adrenaline.network.socket.server;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.Lobby;
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
        super.setOnline (true);
        super.getServer ().logClient (this);
        super.setCurrentLobby(super.getServer().getOpenLobby());
        try {
            while (socket.isConnected ()) {
                DataForServer receivedData = (DataForServer) in.readObject ();
                super.getCurrentLobby ( ).getController ( ).receiveData (receivedData);
            }
        } catch (Exception e) {
            System.err.println (e.getMessage());
            super.setOnline (false);
        } finally {
            System.out.println (super.getNickName ().toUpperCase() + " disconnected");
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
            socket.close ( );
            in.close();
            out.close();
        } catch(IOException e) {
            System.err.println (e.getMessage());
        }
    }

}
