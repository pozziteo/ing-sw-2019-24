package adrenaline.network.rmi;

import adrenaline.data.data_for_server.DataForServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that declares methods callable by a RMI client on a RMI server, it is the server representation
 * uploaded on the RMI registry which clients looks up for when trying to connect
 */
public interface RmiServerInterface extends Remote {

    /**
     * Method to register a client on RMI server, clients use it passing "themselves" to create a server-side
     * representation
     * @param client is the client who is connecting to RMI server
     * @throws RemoteException if an error occurs in the communication on the net
     */
    void registerClient(RmiClientCallbackInterface client) throws RemoteException;

    /**
     * Method used by RMI clients when sending some data to RMI server
     * @param client is the client who is sending data
     * @param data is the pack of data sent
     * @throws RemoteException if an error occurs in the communication on the net
     */
    void receiveData(RmiClientCallbackInterface client, DataForServer data) throws RemoteException;
}
