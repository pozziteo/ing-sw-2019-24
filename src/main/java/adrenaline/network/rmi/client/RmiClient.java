package adrenaline.network.rmi.client;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.ClientInterface;
import adrenaline.network.rmi.RmiClientCallbackInterface;
import adrenaline.network.rmi.RmiServerInterface;
import adrenaline.utils.ReadConfigFile;
import adrenaline.view.UserInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class used to connect a generic client to the server after choosing rmi option.
 */

public class RmiClient extends UnicastRemoteObject implements ClientInterface, RmiClientCallbackInterface {
    private transient UserInterface view;
    private transient RmiServerInterface stub;

    public RmiClient(UserInterface view) throws RemoteException {
        super();
        this.view = view;
    }


    public boolean isConnected() {
        return this.stub != null;
    }

    /**
     * Method to connect the client to the server
     */

    public void connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry(ReadConfigFile.readConfigFile("rmiPort"));
            this.stub = (RmiServerInterface) registry.lookup("RmiServerClientHandler");
            stub.registerClient(this);

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
            stub.receiveData(this, data);
        } catch (RemoteException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void notifyChanges(DataForClient data) throws RemoteException {
        view.updateView(data);
    }

    @Override
    public boolean ping() throws RemoteException {
        return isConnected();
    }

}
