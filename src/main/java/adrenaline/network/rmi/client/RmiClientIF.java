package adrenaline.network.rmi.client;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiClientIF extends Remote {

    String ping() throws RemoteException;
    String addedClient() throws RemoteException;

}
