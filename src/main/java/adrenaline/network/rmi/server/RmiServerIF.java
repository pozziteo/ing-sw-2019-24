package adrenaline.network.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerIF extends Remote {

    //REMEMBER: EVERY METHOD MUST THROW RemoteException
    //callable methods must be written here

    String Hello() throws RemoteException;
    String addedClient(int n) throws RemoteException;
    void send(String msg) throws RemoteException;
}
