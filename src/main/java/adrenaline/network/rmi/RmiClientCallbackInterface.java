package adrenaline.network.rmi;

import adrenaline.data.data_for_client.DataForClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiClientCallbackInterface extends Remote {

    void notifyChanges(DataForClient data) throws RemoteException;
    boolean ping() throws RemoteException;
}
