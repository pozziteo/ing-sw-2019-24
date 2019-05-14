package adrenaline.network.rmi.server;

import adrenaline.network.rmi.RmiImplementation;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class that implements a server with rmi connection.
 */

public class RmiServer implements Runnable {
    private boolean running;
    private int port;

    /**
     * Constructor that creates a RmiServer
     */
    public RmiServer(int port) {
        this.port = port;
        this.running = false;
    }


    /**
     * Method that runs the RmiServer
     * It stops when boolean "running" becomes false
     */
    public void run() {
        try {
            this.running = true;

            RmiImplementation skeleton = new RmiImplementation();
            Registry registry= LocateRegistry.createRegistry(this.port);
            registry.bind("rmiObject", skeleton);
            System.out.println("Waiting for invocations from clients...");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            this.running = false;
        }
    }

    /**
     * Getter for the running state of the server
     * @return boolean that indicates if the server is running or not
     */

    public boolean isRunning() {
        return this.running;
    }
}
