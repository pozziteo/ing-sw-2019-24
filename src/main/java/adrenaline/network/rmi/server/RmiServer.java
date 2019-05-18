package adrenaline.network.rmi.server;

import adrenaline.network.MainServer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class that implements a server with rmi connection.
 */

public class RmiServer implements Runnable {

    private MainServer server;
    private int rmiPort;
    private boolean running;

    public RmiServer(MainServer server, int port) {
        this.server = server;
        this.rmiPort = port;
    }

    @Override
    public void run() {
        running = true;
        try {
            RmiClientHandler handler = new RmiClientHandler();
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            registry.rebind("RmiClientHandler", handler);
            System.out.println("Rmi server running and waiting for invocations.");
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
            running = false;
        }
    }

    public boolean isRunning() {
        return this.running;
    }
}
