package adrenaline.network;

import adrenaline.model.GameModel;
import adrenaline.network.rmi.server.RmiServer;
import adrenaline.network.socket.server.SocketServer;

import java.util.ArrayList;

/**
 * Singleton class that represents the main server hosting a game. It implements both socket
 * and rmi types of connection through one instance of SocketServer and one of RmiServer as attributes.
 */

public class MainServer {
    private static MainServer instance;
    private SocketServer socketServer;
    private RmiServer rmiServer;
    private ArrayList<Account> accounts;
    private boolean mainRunning;
    private boolean rmiRunning;
    private boolean socketRunning;
    private String serverAddress;
    private int rmiPort;
    private int socketPort;
    private GameModel gameModel;

    private MainServer() {
        this.accounts = new ArrayList<>();
        this.serverAddress = "localhost"; //change to get dynamically
        this.rmiPort = 5555;
        this.socketPort = 6666;
        this.mainRunning = false;
        this.rmiRunning = false;
        this.socketRunning = false;
    }

    /**
     * Method to obtain the instance of MainServer
     * @return instance of MainServer
     */

    private static MainServer getInstance() {
        if (instance == null) {
            instance = new MainServer ();
        }
        return instance;
    }

    /**
     * Main method to start and shut down the server
     * @param args passed to main
     */

    public static void main(String[] args) {
        instance = getInstance ();
        instance.startServer();
        instance.shutDown();
    }

    /**
     * Method to run socketServer and rmiServer
     */

    private void startServer() {
        this.gameModel = new GameModel ();
        mainRunning = true;
        while(mainRunning && !socketRunning) {
            try {
                this.socketServer = new SocketServer (getInstance (), socketPort);
                socketRunning = true;
                socketServer.startServer ();
            } catch (Exception e) {
                System.out.println (e);
                mainRunning = false;
            }
        }
        while(mainRunning && !rmiRunning) {
            try {
                this.rmiServer = new RmiServer (rmiPort);
                rmiRunning = true;
                rmiServer.startServer ();
            } catch (Exception e) {
                System.out.println (e);
                mainRunning = false;
            }
        }
    }

    /**
     * Method to shut down MainServer
     */

    private void shutDown() {
        System.out.println ("Server shutting down...");
        System.exit (0);
    }

    /**
     * Getter to obtain game
     */

    public GameModel getGame() {
        return this.gameModel;
    }

}
