package network.socket;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    private boolean running;
    private int port;
    private ServerSocket ss;

    /**
     * This Method creates the server
     *
     * @param port is the port required for creating the socket
     */
    public SocketServer(int port) {
        this.port = port;
        this.running = false;
    }


    /**
     * This method starts the server;
     * It also handles every new client that joins the server
     * and allows bidirectional communication between client and server
     */
    public void startServer() {
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
                    executor.submit(new ServerThread (s, i));
                } catch (IOException e) {
                    running = false;
                    System.out.println (e.getMessage ( ));
                    e.printStackTrace ( );
                }
            }
        executor.shutdown ();
    }


    /**
     * Main Method
     * @param args passed to main()
     * @throws IOException thrown by startServer()
     */
    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer (6666);
        socketServer.startServer();
    }
}