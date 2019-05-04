package adrenaline.network.rmi.client;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.rmi.CommonInterface;
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

    public RmiClient(UserInterface view) {
        this.view = view;
    }


    /**
     * Method to connect the client to the server
     */

    public void connectToServer() {
        try {
            Scanner scanner =new Scanner(System.in);
            Registry registry = LocateRegistry.getRegistry(10000);
            CommonInterface stub = (CommonInterface) registry.lookup("CommonInterface");
            int i = 1;
            String newClient = stub.addedClient(i);
            stub.send(newClient);

            System.err.println("connected to the rmiServer!");

           while (true) {
               String msg = scanner.nextLine().trim();
               if(msg.equals("quit")){break;}
               stub.send("From Client"+i+": "+msg);
           }
           System.err.println("Disconnected from the rmiServer");
           String goodbye = "A client left the room: Client";
           stub.send(goodbye+i);
            i++;
            // DataType var = stub.METHOD(); //here you call the methods you need

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