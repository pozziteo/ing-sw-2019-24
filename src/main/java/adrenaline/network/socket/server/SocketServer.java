package adrenaline.network.socket.server;

import adrenaline.network.MainServer;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that implements a server with socket connection.
 */

public class SocketServer implements Runnable {
    private MainServer server;
    private boolean running;
    private int port;
    private ServerSocket ss;

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
        ExecutorService executor = Executors.newCachedThreadPool ( );
        try {
            ss = new ServerSocket (port);
        } catch(IOException e) {
            System.err.println (e.getMessage ());
            return;
        }
        System.out.println ("SocketServer is listening on port: " + port);
            int i = 0;
            while (running) {
                try {
                    Socket s = ss.accept ( );
                    i++;
                    System.out.println ("A new client is here: Client" + i + "\n");
                    executor.submit(new SocketPlayerThread (server, s, null));
                } catch (IOException e) {
                    running = false;
                    System.out.println (e.getMessage ( ));
                    e.printStackTrace ( );
                }
            }
        executor.shutdown ();
    }

    public boolean isRunning() {
        return this.running;
    }
}