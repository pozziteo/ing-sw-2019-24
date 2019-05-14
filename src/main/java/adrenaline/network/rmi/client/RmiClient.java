package adrenaline.network.rmi.client;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.rmi.RmiInterface;
import adrenaline.network.ClientInterface;
import adrenaline.view.UserInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Class used to connect a generic client to the server after choosing rmi option.
 */

public class RmiClient implements ClientInterface {
    private UserInterface view;

    public RmiClient(UserInterface view){
        this.view = view;
    }


    /**
     * Method to connect the client to the server
     */

    public void connectToServer() {
        try {

            Registry registry= LocateRegistry.getRegistry(10000);
            System.out.print("RMI registry bindings: ");
            String[] e = registry.list();
            for (int i=0; i<e.length; i++)
                System.out.println(e[i]);
            String remoteObjectName = "rmiObject";
            RmiInterface stub = (RmiInterface) registry.lookup(remoteObjectName);

            //stub.method...


        }catch (Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Implementation of sendData() from ClientInterface
     * @param data to send
     */

    public void sendData(DataForServer data) {
        //TODO implement
    }

}
