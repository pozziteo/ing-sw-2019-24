package adrenaline.network.socket.client;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.ClientInterface;
import adrenaline.view.UserInterface;

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
    private ObjectInputStream input;
    private ObjectOutputStream output;

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
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            output.flush();
            (new Thread(this)).start();
            view.setUpAccount ();
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
                DataForClient receivedData = (DataForClient) input.readObject();
                view.updateView(receivedData);
            } catch (Exception e) {
                value = false;
                System.err.println(e.getMessage());
            }
        }
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method to write data from the view on the output stream
     * @param data that needs to be sent to the server
     */

    public void sendData(DataForServer data) {
        try {
            output.writeObject(data);
            output.reset();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
