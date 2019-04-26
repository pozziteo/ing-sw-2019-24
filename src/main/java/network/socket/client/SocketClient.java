package network.socket.client;

import data.DataForClient;
import data.DataForServer;
import network.ClientInterface;
import view.UserInterface;

import java.io.*;
import java.net.Socket;

/**
 * Class used to connect a generic client to the server after choosing the socket option.
 */

public class SocketClient implements ClientInterface, Runnable {
    private int port;
    private String serverAddress;
    private Socket socket;
    private UserInterface view;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * constructor of client socket
     * @param port represents the socket port of the server
     * @param serverAddress represents the IP address (on the same machine it might be called localhost)
     */

    public SocketClient(String serverAddress, int port, UserInterface view){
        this.port = port;
        this.serverAddress = serverAddress;
        this.view = view;
    }

    /**
     * Method to connect the client to the server via socket
     */

    public void connectToServer() {
        try {
            socket = new Socket(serverAddress, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            (new Thread(this)).start();
            //view.setUpAccount ();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Implements run() method from Runnable interface
     */

    public void run() {
        boolean value = true;
        while (value) {
            try {
                DataForClient receivedData = (DataForClient) in.readObject();
                view.updateView(receivedData);
            } catch (Exception e) {
                value = false;
                System.out.println(e);
            }
        }
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Method to write data from the view on the output stream
     * @param data that needs to be sent to the server
     */

    public void sendData(DataForServer data) {
        try {
            out.writeObject(data);
            out.reset();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
