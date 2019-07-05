package adrenaline.network.rmi.client;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.ClientInterface;
import adrenaline.network.rmi.RmiClientCallbackInterface;
import adrenaline.network.rmi.RmiServerInterface;
import adrenaline.utils.ConfigFileReader;
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

    /**
     * Create a RMI generic client with a reference to the view he is using
     * @param view is the view of the client (CLI or GUI)
     * @throws RemoteException if an error about UnicastRemoteObject occurs
     */
    public RmiClient(UserInterface view) throws RemoteException {
        super();
        this.view = view;
    }

    /**
     * Method to check if the client still has a reference to the server's stub
     * @return true if the client has a reference to the server, false otherwise
     */
    public boolean isConnected() {
        return this.stub != null;
    }

    /**
     * Method to connect the client to the server
     */
    public void connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry(ConfigFileReader.readConfigFileString("ipAddress"),ConfigFileReader.readConfigFile("rmiPort"));
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
     * @param data to send to server
     */
    public void sendData(DataForServer data) {
        try {
            stub.receiveData(this, data);
        } catch (RemoteException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Notifies data coming from server to update the client's view
     * @param data is the pack of data coming from the server
     * @throws RemoteException if an error occurs in the communication on the net
     */
    @Override
    public void notifyChanges(DataForClient data) throws RemoteException {
        view.updateView(data);
    }

    /**
     * Method called periodically by the server to check if the client is still online and listening
     * @return an ack to notify the server the client is still listening
     * @throws RemoteException if an error occurs in the communication on the net
     */
    @Override
    public boolean ping() throws RemoteException {
        return isConnected();
    }

}
