package network.socket.client;

import data.DataForClient;
import view.cli.CliUserInterface;

import java.io.*;
import java.net.Socket;

public class SocketClient implements Runnable {
    private int port;
    private String serverAddress;
    private Socket socket;
    private CliUserInterface view;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * constructor of client socket
     * @param port represents the socket port of the server
     * @param serverAddress represents the IP address (on the same machine it might be called localhost)
     */
    public SocketClient(String nickname, String serverAddress, int port, CliUserInterface view){
        this.port = port;
        this.serverAddress = serverAddress;
        this.view = view;
    }

    /**
     * This Method starts the client
     */
    public void connectToServer() {
        try {
            socket = new Socket(serverAddress, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            (new Thread(this)).start();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

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
}
