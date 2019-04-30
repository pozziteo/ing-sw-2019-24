package adrenaline.network.rmi;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;
public class WarehouseImpl extends UnicastRemoteObject implements Warehouse {

    private Map<String, Double> prices;

    public WarehouseImpl() throws RemoteException {
        prices = new HashMap<>();
        prices.put("Blackwell Toaster", 24.95);
        prices.put("ZapXpress Microwave Oven", 49.95);
    }
        @Override
        public double getPrice(String description) throws RemoteException {
            Double price = prices.get(description);
            return price == null ? 0 : price;
            }
}