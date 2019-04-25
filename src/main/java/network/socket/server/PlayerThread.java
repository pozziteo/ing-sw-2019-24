package network.socket.server;

import data.DataForClient;
import data.DataForServer;
import network.visitors.Account;

import java.io.*;
import java.net.Socket;

public class PlayerThread extends Account implements Runnable {
    private Socket socket;
    private int clientNum;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean connected;

    public PlayerThread(Socket s, int i) {
        super();
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

    @Override
    public void run() {
        try {
        connected = true;
            while (connected && socket.isConnected ()) {
                DataForServer receivedData = (DataForServer) in.readObject ();
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

    @Override
    public void sendData(DataForClient data) {
        try {
            out.writeObject(data);
            out.reset();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
