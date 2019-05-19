package adrenaline.network.rmi.server;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.MainServer;
import adrenaline.network.rmi.RmiServerInterface;
import adrenaline.network.rmi.RmiClientCallbackInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class RmiServerClientHandler implements RmiServerInterface {

    private MainServer server;
    private Map<RmiClientCallbackInterface, RmiPlayer> rmiClients;

    public RmiServerClientHandler(MainServer server) throws RemoteException {
        this.server = server;
        this.rmiClients = new ConcurrentHashMap<>();
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void registerClient(RmiClientCallbackInterface client) throws RemoteException {
        String token = Integer.toString( new Random().nextInt (1000000000));
        RmiPlayer newPlayer = new RmiPlayer(server, token, this);
        rmiClients.put(client, newPlayer);
        newPlayer.setUpAccount();
        newPlayer.setUpClient();
    }

    @Override
    public void receiveData(RmiClientCallbackInterface client, DataForServer data) throws RemoteException {
        RmiPlayer sender = rmiClients.get(client);
        sender.receiveData(data);
    }

    public void sendDataTo(RmiPlayer player, DataForClient data) {
        for (Map.Entry<RmiClientCallbackInterface, RmiPlayer> entry : rmiClients.entrySet()) {
            if (player.equals(entry.getValue())) {
                RmiClientCallbackInterface receiver = entry.getKey();
                try {
                    receiver.notifyChanges(data);
                } catch (RemoteException exc) {
                    exc.printStackTrace();
                }
                break;
            }
        }
    }
}
