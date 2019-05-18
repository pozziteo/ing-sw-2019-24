package adrenaline.network.rmi.client;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.ClientInterface;
import adrenaline.network.rmi.RmiInterface;
import adrenaline.view.UserInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class used to connect a generic client to the server after choosing rmi option.
 */

public class RmiClient implements ClientInterface {
    private UserInterface view;
    private RmiInterface stub;

    public RmiClient(UserInterface view) {
        this.view = view;
    }


    /**
     * Method to connect the client to the server
     */

    public void connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry(10000);
            this.stub = (RmiInterface) registry.lookup("RmiClientHandler");

            System.out.println("connected to the rmiServer!");
            view.setUpAccount();

        } catch (Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }


    /**
     * Implementation of sendData() from ClientInterface
     * @param data to send
     */

    public void sendData(DataForServer data) {
        try {
            stub.receiveData(data);
        } catch (RemoteException exc) {
            exc.printStackTrace();
        }
    }

}
