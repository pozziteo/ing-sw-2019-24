package adrenaline.network;

import adrenaline.data.data_for_client.data_for_view.AccountResponse;
import adrenaline.network.rmi.server.RmiServer;
import adrenaline.network.socket.server.SocketServer;

import java.io.*;
import java.util.ArrayList;
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
    private ArrayList<Account> storedAccounts;
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

    /**
     * Main method to start and shut down the server
     * @param args passed to main
     */

    public static void main(String[] args) {
        instance = getInstance ();
        instance.startServer();
    }

    /**
     * Method to run socketServer and rmiServer
     */

    private void startServer() {
        this.gameLobbies = new LinkedList<> ();
        loadAccounts ();
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

    /**
     * Method to read account info from ser file in memory.
     */

    private void loadAccounts() {
        System.out.println ("Loading saved accounts...");
        Account a;
        this.storedAccounts = new ArrayList<> ();
        try (FileInputStream f = new FileInputStream (new File (ACCOUNTS));
             ObjectInputStream stream = new ObjectInputStream (f)) {
                while (stream.available () > 0) {
                    a = (Account) stream.readObject ( );
                    this.storedAccounts.add (a);
                }
            } catch (EOFException e){
                System.out.println("No accounts have been saved at this time.");
            } catch (FileNotFoundException e) {
                if (createFile()) {
                    System.out.println ("File created successfully in " + ACCOUNTS);
                } else
                    System.out.println ("File could not be created.");
            } catch (IOException | ClassNotFoundException e) {
            System.err.println (e.getMessage ());
        }
    }

    private boolean createFile() {
        try {
            File f = new File (ACCOUNTS);
            return f.createNewFile ( );
        } catch (IOException e) {
            System.out.println (e.getMessage ());
            return false;
        }
    }

    /**
     * Method to write accounts stored in storedAccounts in ser file in memory.
     * @throws IOException
     */

    private void storeAccounts() throws IOException {
        for (Account account : this.storedAccounts) {
            try (FileOutputStream f = new FileOutputStream (new File (ACCOUNTS));
                 ObjectOutputStream stream = new ObjectOutputStream (f)) {
                stream.writeObject (account);
            }
        }
    }

    /**
     * Method to check if the nickname chosen by a user is valid for registration or not.
     * @param account to register
     * @throws IOException
     */

    public void registerAccount(Account account, String nickname) {
        if (storedAccounts.isEmpty ()) {
            try {
                System.out.println("New account registered by " + nickname);
                account.setNickname (nickname);
                this.storedAccounts.add (account);
                storeAccounts ( );
                new AccountResponse (account, true, "Welcome, " + account.getNickName ( ) + ". Your registration was successful.");
            } catch (IOException e) {
                System.err.println (e.getMessage ());
            }
        } else {
            for (Account a : this.storedAccounts) {
                if (nickname.equals (a.getNickName () )) {
                    if (a.isOnline ()) {
                        System.out.println("Someone tried registering an account already in use: " + nickname);
                        new AccountResponse (account,false, "This nickname is already in use");
                    } else {
                        System.out.println(nickname + " is back");
                        account.setNickname (nickname);
                        account.setGameHistory(a.getGameHistory());
                        new AccountResponse(account,true, "Welcome back, " + account.getNickName ());
                    }
                }
            }
        }
    }

    public List<Lobby> getGameLobbies() {
        return this.gameLobbies;
    }

    public void createLobby(Lobby l) {
        gameLobbies.add (l);
    }
}
