package adrenaline.network;

import adrenaline.data.data_for_view.AccountResponse;
import adrenaline.network.rmi.server.RmiServer;
import adrenaline.network.socket.server.SocketServer;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Singleton class that represents the main server hosting a game. It implements both socket
 * and rmi types of connection through one instance of SocketServer and one of RmiServer as attributes.
 */

public class MainServer {
    private static MainServer instance;
    private SocketServer socketServer;
    private RmiServer rmiServer;
    private ArrayList<Account> storedAccounts;
    private boolean mainRunning;
    private String serverAddress;
    private int rmiPort;
    private int socketPort;
    private LinkedList<Lobby> gameLobbies;

    //attributes that represent the path of the file with all the accounts
    private static final String PATH = "src" + File.separatorChar + "Resources";
    private static final String ACCOUNTS = PATH + File.separatorChar + "accounts.ser";

    private MainServer() {
        this.storedAccounts = new ArrayList<>();
        this.serverAddress = "localhost"; //change to get dynamically
        this.rmiPort = 5555;
        this.socketPort = 6666;
        this.mainRunning = false;
    }

    /**
     * Method to obtain the instance of MainServer
     * @return instance of MainServer
     */

    private static MainServer getInstance() {
        if (instance == null) {
            instance = new MainServer ();
        }
        return instance;
    }

    /**
     * Main method to start and shut down the server
     * @param args passed to main
     */

    public static void main(String[] args) {
        instance = getInstance ();
        instance.startServer();
        instance.shutDown();
    }

    /**
     * Method to run socketServer and rmiServer
     */

    private void startServer() {
        this.gameLobbies = new LinkedList<> ();
        loadAccounts ();
        mainRunning = true;
        while(mainRunning && !socketServer.isRunning()) {
            try {
                this.socketServer = new SocketServer (getInstance (), socketPort);
                socketServer.startServer ();
            } catch (Exception e) {
                System.out.println (e);
                mainRunning = false;
            }
        }
        while(mainRunning && !rmiServer.isRunning()) {
            try {
                this.rmiServer = new RmiServer (rmiPort);
                rmiServer.startServer ();
            } catch (Exception e) {
                System.out.println (e);
                mainRunning = false;
            }
        }
    }

    /**
     * Method to shut down MainServer
     */

    private void shutDown() {
        System.out.println ("Server shutting down...");
        System.exit (0);
    }

    /**
     * Method to read account info from ser file in memory.
     */

    private void loadAccounts() {
        Account a;
        boolean done = false;
        try {
            FileInputStream f = new FileInputStream (new File (PATH + ACCOUNTS));
            try (ObjectInputStream stream = new ObjectInputStream (f)) {
                while (!done) {
                    if (stream.readObject ( ) != null) {
                        a = (Account) stream.readObject ( );
                        this.storedAccounts.add (a);
                    } else done = true;
                }
            } catch (Exception e) {
                System.out.println (e);
            }
        } catch (FileNotFoundException e) {
            System.out.println (e);
        }
    }


    private void storeAccounts() {
        for (Account account : this.storedAccounts) {
            try {
                FileOutputStream f = new FileOutputStream (new File (PATH + ACCOUNTS));
                ObjectOutputStream s = new ObjectOutputStream (f);
                s.writeObject (account);
                f.close ( );
                s.close ( );
            } catch (Exception e) {
                System.out.println (e);
            }
        }
    }

    public void registerAccount(Account account) {
        boolean responseSent = false;
        for (Account a : this.storedAccounts) {
            if (account.getNickName ( ).equals (a.getNickName () )) {
                if (a.isOnline ()) {
                    responseSent = true;
                    new AccountResponse (false, "This nickname is already in use");
                } else {
                    responseSent = true;
                    new AccountResponse(true, "Welcome back, " + account.getNickName ());
                }
            }
        }
        if (!responseSent) {
            this.storedAccounts.add (account);
            storeAccounts ();
        }
    }

    public List<Lobby> getGameLobbies() {
        return this.gameLobbies;
    }

    public void createLobby(Lobby l) {
        gameLobbies.add (l);
    }
}
