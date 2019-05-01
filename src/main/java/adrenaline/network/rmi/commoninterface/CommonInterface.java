package adrenaline.network.rmi.commoninterface;

import adrenaline.view.cli.CliPrinter;
import adrenaline.view.cli.CliUserInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CommonInterface extends Remote {

    //REMEMBER: EVERY METHOD MUST THROWS RemoteException
    //callable methods must be written here

    String Hello() throws RemoteException;
    String addedClient(int n) throws RemoteException;
    void send(String msg) throws RemoteException;
}
