package adrenaline.network;

import adrenaline.data.data_for_client.data_for_network.AccountResponse;
import adrenaline.data.data_for_client.data_for_network.MessageForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.data.data_for_server.data_for_game.DataForController;
import adrenaline.exceptions.ClosedLobbyException;
import adrenaline.exceptions.GameStartedException;
import adrenaline.network.rmi.server.RmiServer;
import adrenaline.network.socket.server.SocketServer;
import adrenaline.utils.ConfigFileReader;

import java.io.*;
import java.util.LinkedList;
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

    public MainServer() {
        this.onlineClients = new LinkedList<>();
        this.serverAddress = "localhost"; //change to get dynamically
        this.rmiPort = ConfigFileReader.readConfigFile("rmiPort");
        this.socketPort = ConfigFileReader.readConfigFile("socketPort");
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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        instance = getInstance ();
        instance.startServer();
    }

    /**
     * Method to start the server and run socketServer and rmiServer
     */

    private void startServer() throws IOException, ClassNotFoundException {
//        System.setProperty("java.rmi.server.hostname", "192.168.43.155");
        this.gameLobbies = new LinkedList<> ();
        this.storedAccounts = loadAccounts ();
        mainRunning = true;
        ExecutorService executor = Executors.newCachedThreadPool ( );
        while(mainRunning && !rmiRunning) {
            try {
                this.rmiServer = new RmiServer (getInstance(), rmiPort);
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
     * This method is used to receive data from clients
     * @param sender is the client that sent the data
     * @param data is the data sent
     */

    public void receiveData(Account sender, DataForServer data) {
        if (findClient (sender.getNickName ()).getCurrentLobby () == null) {
            data.updateServer (this);
        } else {
            findClient (sender.getNickName ()).getCurrentLobby ().getController ().receiveData ((DataForController) data);
        }
    }

    /**
     * Method to add a client to the list of online clients
     * @param account that logged in
     */

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
            System.err.println("File " + ACCOUNTS + " is empty.");
        } catch(FileNotFoundException e) {
            if (createFile()) {
                System.out.println ("File created successfully in " + ACCOUNTS);
            } else
                System.out.println ("File could not be created.");
        }
        return list;
    }

    /**
     * Method used to create the accounts.ser file
     * @return true if created, false otherwise
     */

    private boolean createFile() {
        try {
            File f = new File (ACCOUNTS);
            return f.createNewFile ( );
        } catch (IOException e) {
            System.out.println (e);
            return false;
        }
    }

    /**
     * Method to write accounts stored in storedAccounts in ser file in memory.
     * @throws IOException from writing on file
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
        if (toRegister != null) {
            boolean alreadyRegistered = checkAlreadyRegistered(toRegister, newNickname);
            if (!alreadyRegistered) {
                saveNewAccount (toRegister, oldNickname, newNickname);
            }
        } else {
            System.err.print ("Client not found.");
        }
    }

    /**
     * This method is used to check if a username chosen is already registered
     * @param toRegister is the account with the username to check
     * @param newNickname is the username to check
     * @return true if already registered, false otherwise
     */

    private boolean checkAlreadyRegistered(Account toRegister, String newNickname) {
        for (Account storedAccount : this.storedAccounts) {
            if (newNickname.equals (storedAccount.getNickName () )) {
                if (findClient (newNickname) != null) {
                    System.out.println("Someone tried registering an account already in use: " + storedAccount.getNickName ());
                    sendLoginResponse (toRegister, false, "This nickname is already in use.\n");
                } else {
                    System.out.println(storedAccount.getNickName () + " is back");
                    toRegister.setNickname (newNickname);
                    toRegister.setGameHistory(storedAccount.getGameHistory());
                    sendLoginResponse (toRegister, true, "Welcome back, " + storedAccount.getNickName () + ".\nYou joined a lobby. Please wait...\n");
                    tryInsertingIntoLobby (toRegister);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to save a new account on the server
     * @param account saved
     * @param oldNickname is the default nickname
     * @param newNickname is the chosen nickname
     */

    private void saveNewAccount(Account account, String oldNickname, String newNickname) {
        try {
            System.out.println ("New account registered by " + oldNickname + " -> " + newNickname);
            account.setNickname (newNickname);
            this.storedAccounts.add (account);
            storeAccounts ( );
            sendLoginResponse (account, true, "Welcome, " + account.getNickName ( ) + ". Your registration was successful.\nYou joined a lobby. Please wait...\n");
            tryInsertingIntoLobby (account);
        } catch (IOException e) {
            System.err.println (e.getMessage ( ));
        }
    }

    /**
     * This method is used to tell the client whether or not the registration was successful
     * @param a to send response to
     * @param successful true if successful, false otherwise
     * @param message is the message to send to the client
     */

    private void sendLoginResponse(Account a, boolean successful, String message) {
        AccountResponse response = new AccountResponse (a, successful, message);
        response.sendToView ();
    }

    /**
     * This method is used to insert a client in a lobby
     * @param toRegister is the account to insert
     */

    private void tryInsertingIntoLobby(Account toRegister) {
        boolean disconnectedDuringGame = false;

        for (Lobby l : gameLobbies) {
            if (l.getDisconnected ().contains(toRegister.getNickName ())) {
                disconnectedDuringGame = true;
                toRegister.insertBackIntoLobby (l);
                break;
            }
        }

        if (! disconnectedDuringGame) {
            try {
                toRegister.setCurrentLobby (getOpenLobby ( ));
            } catch (GameStartedException e) {
                MessageForClient message = new MessageForClient (toRegister, e.getMessage ( ));
                message.sendToView ( );
            }
        }
    }

    /**
     * This method is used to find a client in the list of online clients
     * @param nickname is the nickname of the client
     * @return the account
     */

    public Account findClient(String nickname) {
        for (Account client : onlineClients) {
            if (nickname.equals(client.getNickName ())) {
                return client;
            }
        }
        return null;
    }

    /**
     * This method is used to obtain an open lobby for a client
     * @return the open lobby
     */

    public Lobby getOpenLobby() {
        if (gameLobbies.isEmpty()) {
            createLobby(new Lobby(0,this));
            return gameLobbies.getFirst();
        } else {
            for (Lobby lobby : gameLobbies) {
                if (!lobby.isFull() && !lobby.isGameStarted()) {
                    return lobby;
                }
            }
            createLobby(new Lobby(gameLobbies.size ()-1, this));
            return gameLobbies.getLast();
        }
    }

    /**
     * This method is used to add a new lobby
     * @param l to add
     */

    private void createLobby(Lobby l) {
        gameLobbies.add (l);
    }

    /**
     * This method is used to remove a lobby
     * @param l to remove
     */

    public void removeLobby(Lobby l) {
        gameLobbies.remove(l);
    }

    /**
     * This method is used to tell the lobby that a client disconnected
     * @param disconnectedNickname is the name of the disconnected client
     */

    public void notifyDisconnection(String disconnectedNickname) {
        Account disconnected = findClient (disconnectedNickname);
        Lobby toNotify = disconnected.getCurrentLobby ();
        try {
            toNotify.removeDisconnected (disconnected);
        } catch (ClosedLobbyException e) {
            this.gameLobbies.remove(disconnected.getCurrentLobby ());
        }
        this.onlineClients.remove(disconnected);
    }

}
