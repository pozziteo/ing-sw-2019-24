package network.socket;

import data.DataForClient;
import view.UserInterface;

import java.io.*;
import java.net.Socket;

public class SocketClient implements Runnable {
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
     * This Method starts the client
     */
    public void connect() {
        try {
            socket = new Socket("localhost", 6666);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            (new Thread(this)).start();
            in.close();
            out.close();
            socket.close();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void run() {
        boolean value = true;
        while (value) {
            try {
                DataForClient receivedData = (DataForClient) in.readObject();
                view.update(receivedData);
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
}
