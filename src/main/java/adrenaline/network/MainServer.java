package adrenaline.network;

import adrenaline.data.data_for_client.data_for_view.AccountResponse;
import adrenaline.network.rmi.server.RmiServer;
import adrenaline.network.socket.server.SocketServer;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Singleton class that represents the main server hosting a game. It implements both socket
 * and rmi types of connection through one instance of SocketServer and one of RmiServer as attributes.
 */

public class MainServer {
    private static MainServer instance;
    private SocketServer socketServer;
    private RmiServer rmiServer;
    private LinkedList<Account> onlineClients;
    private LinkedList<Account> storedAccounts;
    private boolean mainRunning;
    private boolean socketRunning;
    private boolean rmiRunning;
    private String serverAddress;
    private int rmiPort;
    private int socketPort;
    private LinkedList<Lobby> gameLobbies;

    //attributes that represent the path of the file with all the accounts
    private static final String PATH = "src" + File.separatorChar + "Resources";
    private static final String ACCOUNTS = PATH + File.separatorChar + "accounts.ser";

    private MainServer() {
        this.onlineClients = new LinkedList<>();
        this.serverAddress = "localhost"; //change to get dynamically
        this.rmiPort = 10000;
        this.socketPort = 6666;
        this.mainRunning = false;
        this.socketRunning = false;
        this.rmiRunning = false;
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

    public List<Account> getStoredAccounts() {
        return this.storedAccounts;
    }

    /**
     * Main method to start and shut down the server
     * @param args passed to main
     */

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        instance = getInstance ();
        instance.startServer();
    }

    /**
     * Method to run socketServer and rmiServer
     */

    private void startServer() throws IOException, ClassNotFoundException {
        this.gameLobbies = new LinkedList<> ();
        this.storedAccounts = loadAccounts ();
        mainRunning = true;
        ExecutorService executor = Executors.newCachedThreadPool ( );
        while(mainRunning && !rmiRunning) {
            try {
                this.rmiServer = new RmiServer (rmiPort);
                rmiRunning = true;
                executor.submit(rmiServer);
            } catch (Exception e) {
                System.err.println (e.getMessage ());
                mainRunning = false;
                rmiRunning = rmiServer.isRunning ();
            }
        }
        mainRunning = true;
        while(mainRunning && !socketRunning) {
            try {
                this.socketServer = new SocketServer (getInstance (), socketPort);
                socketRunning = true;
                executor.submit (socketServer);
            } catch (Exception e) {
                System.err.println (e.getMessage ());
                mainRunning = false;
                socketRunning = socketServer.isRunning ();
            }
        }

    }

    /**
     * Method to shut down MainServer
     */

    private void shutDown() throws IOException {
        storeAccounts ();
        System.out.println ("Server shutting down...");
        System.exit (0);
    }

    public void logClient(Account account) {
        this.onlineClients.add (account);
    }

    /**
     * Method to read account info from ser file in memory.
     */

    private LinkedList<Account> loadAccounts() throws IOException, ClassNotFoundException {
        LinkedList<Account> list = new LinkedList<>();
        System.out.println ("Loading saved accounts...");
        try (FileInputStream file = new FileInputStream (new File (ACCOUNTS));
             ObjectInputStream stream = new ObjectInputStream (file)) {
            list = (LinkedList<Account>) stream.readObject();
            System.out.print ("Accounts list: [ ");
            for (Account a : list) {
                System.out.print(a.getNickName () + " ");
            }
            System.out.print ("]\n");
        } catch(EOFException e) {
            System.err.println("File " + ACCOUNTS + " is empty. Registered accounts could not be loaded.");
        }
        return list;
    }

    /**
     * Method to write accounts stored in storedAccounts in ser file in memory.
     * @throws IOException
     */

    private void storeAccounts() throws IOException {
        try (FileOutputStream f = new FileOutputStream (new File (ACCOUNTS));
             ObjectOutputStream stream = new ObjectOutputStream (f)) {
            stream.writeObject (this.storedAccounts);
        }
    }

    /**
     * Method to change the default nickname to a custom one and save the client's data
     * @param oldNickname to change
     * @param newNickname to set instead of the old one
     */

    public void registerAccount(String oldNickname, String newNickname) {
        Account toRegister = findClient (oldNickname);
        boolean alreadyRegistered;
        if (toRegister != null) {
            if (storedAccounts.isEmpty ( )) {
               saveNewAccount (toRegister, oldNickname, newNickname);
            } else {
                alreadyRegistered = checkAlreadyRegistered(toRegister, newNickname);
                if (!alreadyRegistered) {
                    saveNewAccount (toRegister, oldNickname, newNickname);
                }
            }
        } else {
            System.err.print ("Client not found.");
        }
    }

    private boolean checkAlreadyRegistered(Account toRegister, String newNickname) {
        for (Account storedAccount : this.storedAccounts) {
            if (newNickname.equals (storedAccount.getNickName () )) {
                if (storedAccount.isOnline ()) {
                    System.out.println("Someone tried registering an account already in use: " + storedAccount.getNickName ());
                    sendLoginResponse (toRegister, false, "This nickname is already in use");
                } else {
                    System.out.println(storedAccount.getNickName () + " is back");
                    toRegister.setNickname (newNickname);
                    toRegister.setGameHistory(storedAccount.getGameHistory());
                    sendLoginResponse (toRegister, true, "Welcome back, " + storedAccount.getNickName ());
                }
                return true;
            }
        }
        return false;
    }

    private void saveNewAccount(Account account, String oldNickname, String newNickname) {
        try {
            System.out.println ("New account registered by " + oldNickname + " -> " + newNickname);
            account.setNickname (newNickname);
            this.storedAccounts.add (account);
            storeAccounts ( );
            sendLoginResponse (account, true, "Welcome, " + account.getNickName ( ) + ". Your registration was successful.");
        } catch (IOException e) {
            System.err.println (e.getMessage ( ));
        }
    }

    private void sendLoginResponse(Account a, boolean successful, String message) {
        AccountResponse response = new AccountResponse (a, successful, message);
        response.sendToView ( );
    }

    public Account findClient(String nickname) {
        for (Account client : onlineClients) {
            if (nickname.equals(client.getNickName ())) {
                return client;
            }
        }
        return null;
    }

    public Lobby getOpenLobby() {
        if (gameLobbies.isEmpty()) {
            createLobby(new Lobby(this));
            return gameLobbies.getFirst();
        } else {
            for (Lobby lobby : gameLobbies) {
                if (!lobby.isFull()) {
                    return lobby;
                }
            }
            createLobby(new Lobby(this));
            return gameLobbies.getLast();
        }
    }

    private void createLobby(Lobby l) {
        gameLobbies.add (l);
    }
}
