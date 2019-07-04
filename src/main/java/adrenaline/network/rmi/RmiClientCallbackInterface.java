package adrenaline.network.rmi;

import adrenaline.data.data_for_client.DataForClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that declares methods callable by the RMI server on a RMI client, it is used by the server to identify
 * each client
 */
public interface RmiClientCallbackInterface extends Remote {

    /**
     * Invoked when server is sending a pack of data to the client.
     * @param data is the pack of data to be sent
     * @throws RemoteException if an error occurs in the communication on the net
     */
    void notifyChanges(DataForClient data) throws RemoteException;

    /**
     * Invoked to check if the client is still online and listening
     * @return true if the client is still online, false otherwise
     * @throws RemoteException if an error occurs in the communication on the net
     */
    boolean ping() throws RemoteException;
}
