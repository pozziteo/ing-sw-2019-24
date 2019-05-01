package adrenaline.network.rmi.commoninterface;

import adrenaline.view.cli.CliPrinter;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CommonInterface extends Remote {

    //REMEMBER: EVERY METHOD MUST THROWS RemoteException
    //callable methods must be written here

    String mapSelectorRmi() throws RemoteException;
    void sendMethod(CliPrinter printer) throws RemoteException;
    void send(String msg) throws RemoteException;
}
