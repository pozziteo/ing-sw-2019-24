package adrenaline.network.rmi;

import java.rmi.*; import java.rmi.registry.*;
import java.util.*;
import javax.naming.*;
public class WarehouseClient {
        public static void main(String[] args)
                throws NamingException, RemoteException, NotBoundException {
                Registry registry= LocateRegistry.getRegistry();
                System.out.print("RMI registry bindings: ");
                 String[] e = registry.list();
                for (int i=0; i<e.length; i++)
                System.out.println(e[i]);
                String remoteObjectName = "central_warehouse";
                Warehouse centralWarehouse = (Warehouse) registry.lookup(remoteObjectName);
                String descr = "Blackwell Toaster";
                double price = centralWarehouse.getPrice(descr);
                System.out.println(descr + ": " + price);
        }
}