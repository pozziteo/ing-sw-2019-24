package network;

import java.io.*;
import java.net.Socket;

public class Client {

    private int port;
    private String serverAddress;

    /**
     * constructor of client socket
     * @param port represents the socket port of the server
     * @param serverAddress represents the IP address (on the same machine it might be called localhost)
     */
    public Client(String serverAddress, int port){
        this.port = port;
        this.serverAddress = serverAddress;
    }

    /**
     * This Method starts the client
     */
    public void startClient() throws IOException {
        Socket socket = null;

        try {
            socket = new Socket("localhost", 6666);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (socket != null) {
                socket.close ( );
            }
        }
    }
}
