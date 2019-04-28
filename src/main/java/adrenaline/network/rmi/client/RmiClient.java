package adrenaline.network.rmi.client;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.rmi.commoninterface.CommonInterface;
import adrenaline.network.ClientInterface;
import adrenaline.view.UserInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class used to connect a generic client to the server after choosing rmi option.
 */

public class RmiClient implements ClientInterface, CommonInterface {
    private UserInterface view;

    public RmiClient(UserInterface view) {
        this.view = view;
    }

    /**
     * Method to connect the client to the server
     */

    public void connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry(5555);
            CommonInterface stub = (CommonInterface) registry.lookup("CommonInterface");

            /* DataType var = stub.METHOD(); //here you call the methods you need
            System.out.println("what you called: " +var);
            */
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
