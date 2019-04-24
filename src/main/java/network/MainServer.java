package network;

import network.rmi.RmiServer;
import network.socket.SocketServer;
import view.cli.CliParser;

public class MainServer {
    private static MainServer instance;
    private SocketServer socketServer;
    private RmiServer rmiServer;
    private boolean mainRunning;
    private boolean rmiRunning;
    private boolean socketRunning;
    private String serverAddress;
    private int rmiPort;
    private int socketPort;
    private CliParser parser;

    private MainServer() {
        this.parser = new CliParser ();
        this.serverAddress = "localhost"; //change to get dynamically
        this.rmiPort = 5555;
        this.socketPort = 6666;
        this.mainRunning = false;
        this.rmiRunning = false;
        this.socketRunning = false;
    }

    private static MainServer getInstance() {
        if (instance == null) {
            instance = new MainServer ();
        }
        return instance;
    }

    public static void main(String[] args) {
        instance = getInstance ();
        instance.startServer();
        instance.shutDown();
    }

    private void startServer() {
        mainRunning = true;
        while(mainRunning && !socketRunning) {
            try {
                this.socketServer = new SocketServer (socketPort);
                socketRunning = true;
                socketServer.startServer ();
            } catch (Exception e) {
                System.out.println (e);
                mainRunning = false;
            }
        }
        while(mainRunning && !rmiRunning) {
            try {
                this.rmiServer = new RmiServer ();
                rmiRunning = true;
                rmiServer.startServer ();
            } catch (Exception e) {
                System.out.println (e);
                mainRunning = false;
            }
        }
    }

    private void shutDown() {
        System.out.println ("Server shutting down...");
        System.exit (0);
    }
}
