package adrenaline.network.socket.server;

import adrenaline.network.MainServer;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that implements a server with socket connection.
 */

public class SocketServer implements Runnable {
    private MainServer server;
    private boolean running;
    private int port;
    private ServerSocket serverSocket;

    /**
     * This Method creates the server
     *
     * @param port is the port required for creating the socket
     */
    public SocketServer(MainServer server, int port) {
        this.server = server;
        this.port = port;
        this.running = false;
    }


    /**
     * This method starts the server;
     * It also handles every new client that joins the server
     * and allows bidirectional communication between client and server
     */
    public void run() {
        running = true;
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket (port);
        } catch(IOException e) {
            System.err.println (e.getMessage());
            return;
        }
        System.out.println ("SocketServer is listening on port: " + port);
            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println ("A new client is here\n");
                    executor.submit(new SocketPlayerThread (server, socket, Integer.toString( new Random().nextInt (1000000000))));
                } catch (IOException e) {
                    running = false;
                    System.err.println (e.getMessage());
                }
            }
        executor.shutdown();
    }

    /**
     * Getter method to know if the server is running
     * @return true if running, false otherwise
     */

    public boolean isRunning() {
        return this.running;
    }
}