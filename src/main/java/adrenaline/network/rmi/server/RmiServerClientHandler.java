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

    private class CheckConnection implements Runnable {

        private RmiClientCallbackInterface client;
        private RmiPlayer playerClient;

        public CheckConnection(RmiClientCallbackInterface client, RmiPlayer player) {
            this.client = client;
            this.playerClient = player;
        }

        @Override
        public void run() {
            boolean connected = true;
            try {
                while (connected) {
                    Thread.sleep(200);
                    connected = client.ping();
                }
            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            } catch (RemoteException exc) {
                System.out.println(exc.getMessage());
            } finally {
                playerClient.setOnline(false);
                closeConnection(client, playerClient);
            }
         }
    }

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
        new Thread(new CheckConnection(client, newPlayer)).start();
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

    public void closeConnection(RmiClientCallbackInterface remoteClient, RmiPlayer serverClient) {
        System.out.println(serverClient.getNickName() + " disconnected");
        serverClient.setOnline(false);
        serverClient.getServer().notifyDisconnection(serverClient.getNickName());
        rmiClients.remove(remoteClient);
    }
}
