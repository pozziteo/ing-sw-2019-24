package adrenaline.network.rmi;

import adrenaline.data.data_for_server.DataForServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiInterface extends Remote {

    //REMEMBER: EVERY METHOD MUST THROW RemoteException
    //callable methods must be written here
    void receiveData(DataForServer data) throws RemoteException;
}
