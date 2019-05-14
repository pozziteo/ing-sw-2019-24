package adrenaline.network.rmi;

import adrenaline.data.data_for_server.DataForServer;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiImplementation extends UnicastRemoteObject implements RmiInterface {

    public RmiImplementation() throws RemoteException {    }
    public void send(DataForServer data) throws RemoteException{    }
    public void setUpAccount() throws RemoteException{    }
}
