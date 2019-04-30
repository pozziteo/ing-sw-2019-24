package adrenaline.network.socket.server;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.MainServer;
import adrenaline.network.Account;

import java.io.*;
import java.net.Socket;

/**
 * Class that implements the thread (server side) for every single client connected.
 */

public class PlayerThread extends Account implements Runnable {
    private transient Socket socket;
    private transient ObjectInputStream in;
    private transient ObjectOutputStream out;

    public PlayerThread(MainServer server, Socket s, String nickname) {
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
        try {
            while (socket.isConnected ()) {
                DataForServer receivedData = (DataForServer) in.readObject ();
                this.getCurrentLobby ( ).getController ( ).receiveData (receivedData);
            }
        } catch (Exception e) {
            System.out.println (e);
            super.setOnline (false);
        } finally {
            System.out.println ("Client " + super.getNickName () + " disconnected");
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
            out.reset();
        } catch (IOException e) {
            System.out.println(e);
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
            System.out.println (e);
        }
    }

}
