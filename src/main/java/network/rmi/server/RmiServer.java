package network.rmi.server;

import network.rmi.commoninterface.CommonInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer implements CommonInterface{
    private boolean running;
    private int port;

    /**
     * Constructor that creates a not-running RmiServer
     */
    public RmiServer(int port) {
        this.port = port;
        this.running = false;
    }

    /**
     * this Method runs the RmiServer on default port 1099
     * It stops when boolean "running" becomes false
     */
    public void startServer() {
        running = true;
        while(running) {
            try {

                RmiServer server = new RmiServer(port);
                CommonInterface stub = (CommonInterface) UnicastRemoteObject.exportObject(server, 0);

                //bind the remote object's stub in the registry
                Registry registry = LocateRegistry.getRegistry();
                registry.bind("CommonInterface", stub);
                System.err.println("RmiServer ready on default port 1099");

            } catch (Exception e) {
                System.err.println(e.getMessage());
                running = false;
            }
        }
    }

    /**
     * this Method that shuts down the server
     */
    public void shutDownRmiServer(){
        running = false;
    }

    /**
     * getter Method of the running state of the server
     * @return a boolean that indicates if the server is running or not
     */
    public boolean getRunning() {
        return this.running;
    }
}
