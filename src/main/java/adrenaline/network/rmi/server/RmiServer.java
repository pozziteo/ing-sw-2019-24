package adrenaline.network.rmi.server;

import adrenaline.network.rmi.commoninterface.CommonInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class that implements a server with rmi connection.
 */

public class RmiServer extends RmiServerImpl {
    private boolean running;
   // private int port;

    /**
     * Constructor that creates a RmiServer
     */
    public RmiServer() throws RemoteException {

        this.running = false;
    }

    /**
     * Method that runs the RmiServer
     * It stops when boolean "running" becomes false
     */
    private int i=1;
    public void startServer() throws RemoteException{
        running = true;

       // while(running) {
            try {
                Registry registry = LocateRegistry.createRegistry(10000);
                // Instantiating the implementation class
                RmiServerImpl obj = new RmiServerImpl();

                // Exporting the object of implementation class
                // (here we are exporting the remote object to the stub)
                CommonInterface skeleton = (CommonInterface) UnicastRemoteObject.exportObject(obj, 0);

                // Binding the remote object (stub) in the registry


                registry.bind("CommonInterface", skeleton);
                System.err.println("Server ready on port 10000, "+i+"round");
                i++;
                while(running){

                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                shutDownRmiServer();
            }
       // }
    }

    /**
     * Method that shuts down the server
     */

    public void shutDownRmiServer(){
        running = false;
    }

    /**
     * Getter for the running state of the server
     * @return boolean that indicates if the server is running or not
     */

    public boolean isRunning() {
        return this.running;
    }
}
