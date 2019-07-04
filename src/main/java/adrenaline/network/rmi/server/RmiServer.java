package adrenaline.network.rmi.server;

import adrenaline.network.MainServer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class that implements a server that manages RMI connection.
 */

public class RmiServer implements Runnable {

    private MainServer server;
    private int rmiPort;
    private boolean running;

    /**
     * Creates the RMI server and make it listening on the given port
     * @param server is the main server which manage data
     * @param port is the port where the server is listening
     */
    public RmiServer(MainServer server, int port) {
        this.server = server;
        this.rmiPort = port;
    }

    /**
     * Starts the RMI server by creating a RmiClientHandler to manage communication between clients and server and
     * uploading it on the RMI registry
     */
    @Override
    public void run() {
        running = true;
        try {
            RmiServerClientHandler handler = new RmiServerClientHandler(server);
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            registry.rebind("RmiServerClientHandler", handler);
            System.out.println("Rmi server running and waiting for invocations.");
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
            running = false;
        }
    }

    /**
     * Checks if the server is up and running
     * @return true if the server is listening
     */
    public boolean isRunning() {
        return this.running;
    }
}
