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

/**
 * Class that manages communication between clients and server in RMI communication. It has a Map attribute that matches
 * each client to the server representation of client (that is, a couple ClientCallBack-RmiPlayer) to send data to the right
 * clients when needed. This class may be considered as some kind of router that routes data in the correct directions
 */
public class RmiServerClientHandler implements RmiServerInterface {

    private MainServer server;
    /**
     * The attribute map each client to its representation server-side to be sure all data are sent to the correct
     * receiver
     */
    private Map<RmiClientCallbackInterface, RmiPlayer> rmiClients;

    /**
     * Inner class that instantiates for each client a Thread used to check that clients are still connected to the
     * server and didn't crashed or exited. Contain for each client the couple client-side and server-side to
     * identify each client
     */
    private class CheckConnection implements Runnable {

        private RmiClientCallbackInterface client;
        private RmiPlayer playerClient;

        /**
         * Create a new checker for the client identified by the given parameters client and server-side
         * @param client is the client-side representation of the client
         * @param player is the server-side representation of the client
         */
        public CheckConnection(RmiClientCallbackInterface client, RmiPlayer player) {
            this.client = client;
            this.playerClient = player;
        }

        /**
         * Runs the Thread that pings periodically the client to check if he is still listening. If server doesn't get
         * an answer or a RemoteException is throwed, it closes the connection between client and server and drop from
         * the Map the references to him
         */
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

    /**
     * Creates a new client handler with a reference to the main server which manage data, and uploads the object
     * on the RMI registry
     * @param server is the reference of the main server
     * @throws RemoteException if an error occurs in exporting the object
     */
    public RmiServerClientHandler(MainServer server) throws RemoteException {
        this.server = server;
        this.rmiClients = new ConcurrentHashMap<>();
        UnicastRemoteObject.exportObject(this, 0);
    }

    /**
     * Overrides super method to save the client's data for being able to reach it for communication when events
     * occur by creating a server-side representation of the client and saving in the Map attribute the couple
     * client-server side representations of the client
     * @param client is the client-side representation of the client
     * @throws RemoteException if an error occurs in the communication on the net
     */
    @Override
    public void registerClient(RmiClientCallbackInterface client) throws RemoteException {
        String token = Integer.toString( new Random().nextInt (1000000000));
        RmiPlayer newPlayer = new RmiPlayer(server, token, this);
        rmiClients.put(client, newPlayer);
        newPlayer.setUpAccount();
        newPlayer.setUpClient();
        new Thread(new CheckConnection(client, newPlayer)).start();
    }

    /**
     * Handles data coming from the client and routes them to the correct server-representation by checking on the
     * Map attribute the right corresponding object
     * @param client is the client who just sent a pack of date
     * @param data is the pack of data coming from client
     * @throws RemoteException if an error occurs in the communication on the net
     */
    @Override
    public void receiveData(RmiClientCallbackInterface client, DataForServer data) throws RemoteException {
        RmiPlayer sender = rmiClients.get(client);
        sender.receiveData(data);
    }

    /**
     * Routes a pack of data to the right client-side representation of a client by finding him on the Map attribute,
     * and sends him the pack of date
     * @param player is the server-side representation of a client used to find the client-side representation
     * @param data is the pack of data to be sent
     */
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

    /**
     * Method invoked when a disconnection of a RMI client is noticed, it sets him offline, notify the disconnection
     * to other clients and remove the reference to the client-side object from the Map attribute
     * @param remoteClient is the client-side representation of the client who just disconnected
     * @param serverClient is the server side representation of the client who just disconnected
     */
    public void closeConnection(RmiClientCallbackInterface remoteClient, RmiPlayer serverClient) {
        System.out.println(serverClient.getNickName() + " disconnected");
        serverClient.setOnline(false);
        serverClient.getServer().notifyDisconnection(serverClient.getNickName());
        rmiClients.remove(remoteClient);
    }
}
