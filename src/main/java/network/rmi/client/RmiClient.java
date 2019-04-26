package network.rmi.client;

import network.rmi.commoninterface.CommonInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClient {

    private RmiClient(){}

    public void startRmiClient() {

        try {
            Registry registry = LocateRegistry.getRegistry();
            CommonInterface stub = (CommonInterface) registry.lookup("CommonInterface");

            /* DataType var = stub.METHOD(); //here you call the methods you need
            System.out.println("what you called: " +var);
            */
        }catch (Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
