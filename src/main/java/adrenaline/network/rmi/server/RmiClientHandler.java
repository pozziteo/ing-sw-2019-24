package adrenaline.network.rmi.server;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.rmi.RmiInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiClientHandler implements RmiInterface {

    public RmiClientHandler() throws RemoteException {
        super();
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void receiveData(DataForServer data) throws RemoteException {

    }
}
