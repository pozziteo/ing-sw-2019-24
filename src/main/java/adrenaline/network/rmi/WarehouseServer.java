package adrenaline.network.rmi;

import java.rmi.*;
import java.rmi.registry.*;
public class WarehouseServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException{
        System.out.println("Constructing server implementation...");
        WarehouseImpl centralWarehouse = new WarehouseImpl();
        System.out.println("Binding server implementation to registry...");
        Registry registry= LocateRegistry.getRegistry();
        registry.bind("central_warehouse", centralWarehouse);
        System.out.println("Waiting for invocations from clients...");
    }
}
