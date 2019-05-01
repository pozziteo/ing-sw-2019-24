package adrenaline.network.rmi.client;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.rmi.commoninterface.CommonInterface;
import adrenaline.network.ClientInterface;
import adrenaline.view.UserInterface;
import adrenaline.view.cli.CliPrinter;
import adrenaline.view.cli.CliUserInterface;

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
            CliPrinter printer = new CliPrinter();
            printer.printMapOptions();

            String msg = scanner.nextLine().trim();
            stub.send(msg);


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
