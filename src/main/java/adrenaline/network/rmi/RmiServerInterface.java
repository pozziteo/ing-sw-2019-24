package adrenaline.network.rmi;

import adrenaline.data.data_for_server.DataForServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInterface extends Remote {

    //REMEMBER: EVERY METHOD MUST THROW RemoteException
    //callable methods must be written here
    void registerClient(RmiClientCallbackInterface client) throws RemoteException;

    void receiveData(RmiClientCallbackInterface client, DataForServer data) throws RemoteException;
}
