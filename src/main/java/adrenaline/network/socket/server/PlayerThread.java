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
    private boolean connected;
    private PlayerController controller;

    public PlayerThread(MainServer server, Socket s, int i) {
        this.controller = new PlayerController(server.getGame(), this);
        this.socket = s;
        this.clientNum = i;
        this.connected = true;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush();
        } catch (IOException exc) {
            connected = false;
        }
    }

    /**
     * Implementation of run() method of Runnable interface
     */

    @Override
    public void run() {
        try {
        connected = true;
            while (connected && socket.isConnected ()) {
                DataForServer receivedData = (DataForServer) in.readObject ();
                this.controller.receiveData (receivedData);
            }
        } catch (Exception e) {
            System.out.println (e);
            connected = false;
        } finally {
            System.out.println ("Client " + clientNum + " disconnected");
            connected = false;
        }
        try {
            socket.close ( );
            in.close();
            out.close();
        } catch(IOException e) {
            System.out.println (e);
        }
    }

    /**
     * Method to forward data from server to client
     * @param data meant for the client
     */

    public void sendData(DataForClient data) {
        try {
            out.writeObject(data);
            out.reset();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
