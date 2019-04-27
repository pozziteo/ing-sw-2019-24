package adrenaline.network.socket.server;

import adrenaline.controller.PlayerController;
import adrenaline.data.DataForClient;
import adrenaline.data.DataForServer;
import adrenaline.network.MainServer;
import adrenaline.network.Account;

import java.io.*;
import java.net.Socket;

/**
 * Class that implements the thread (server side) for every single client connected.
 */

public class PlayerThread extends Account implements Runnable {
    private Socket socket;
    private int clientNum;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private PlayerController controller;

    public PlayerThread(MainServer server, Socket s, int i) {
        this.controller = new PlayerController(server.getGame(), this);
        this.socket = s;
        this.clientNum = i;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
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
                this.controller.receiveData (receivedData);
            }
        } catch (Exception e) {
            System.out.println (e);
            super.setOnline (false);
        } finally {
            System.out.println ("Client " + clientNum + " disconnected");
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
