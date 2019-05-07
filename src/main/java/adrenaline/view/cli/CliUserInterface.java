package adrenaline.view.cli;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.data.data_for_server.data_for_game.MapSetUp;
import adrenaline.data.data_for_server.data_for_network.AccountSetUp;
import adrenaline.model.map.Square;
import adrenaline.network.ClientInterface;
import adrenaline.network.rmi.client.RmiClient;
import adrenaline.network.socket.client.SocketClient;
import adrenaline.view.UserInterface;

import java.io.File;

/**
 * Class that implements the user interface using command line.
 */

public class CliUserInterface implements UserInterface {
    private static CliUserInterface instance;
    private CliPrinter printer;
    private CliParser parser;
    private ClientInterface client;
    private String nickname;
    private boolean firstPlayer;

    //attributes that represent the file names for each map
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";
    private static final String MEDIUM_1 = PATH + File.separatorChar + "mediummap_1.json";
    private static final String MEDIUM_2 = PATH + File.separatorChar + "mediummap_2.json";
    private static final String LARGE = PATH + File.separatorChar + "largemap.json";

    public CliUserInterface() {
        this.printer = new CliPrinter ();
        this.parser = new CliParser ();
    }

    /**
     * Getter to create and obtain the instance of this singleton class.
     * It calls the method that establishes connection with the server.
     * @return istance
     */

    public static CliUserInterface getCliInstance() {
        if (instance == null) {
            instance = new CliUserInterface ( );
            instance.establishConnection ();
        }
        return instance;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Setter method for firstPlayer
     */

    public void setFirstPlayer(boolean value) {
        this.firstPlayer = value;
    }

    /**
     * Method to connect this client to the server based on the connection type chosen.
     */

    private void establishConnection() {
        launchTitleScreen ();
        this.printer.printConnectionOptions ();
        if (this.parser.parseInt (1) == 0) {
            this.client = new RmiClient (this);
        } else {
            this.client = new SocketClient ("localhost", 6666, this);
        }
        client.connectToServer ();
    }

    /**
     * Shows title screen on command line.
     */

    public void launchTitleScreen() {
        this.printer.printTitle ();
        this.parser.parseEnter ();
        this.printer.clearScreen ();
    }

    /**
     * Implements UserInterface method. It sends parsed data to the controller.
     * @param data to send
     */

    public void sendToController(DataForServer data) {
        client.sendData (data);
    }

    /**
     * Implements UserInterface method. It updates data received from server.
     * @param data that has to be updated
     */
    public void updateView(DataForClient data) {
        data.updateView(this);
    }

    public void setUpAccount() {
        printer.printNickname ();
        String newNickname = this.parser.parseNickname ();
        AccountSetUp accountData = new AccountSetUp (nickname, newNickname);
        sendToController (accountData);
        printer.print ("Account data sent to server. Waiting for response...\n");
    }

    public void loginStatus(boolean successful, String message) {
        this.printer.print (message);
        if (!successful) {
            this.printer.print ("Try again...\n");
            setUpAccount ();
        }
    }

    public void waitLobby(boolean ready, String message) {
        if (nickname != null) {
            printer.print ("A new player joined your lobby...");
            printer.print (message);
        }
    }


    public void showTimeOutExpired(String message) {
        this.printer.print (message);
    }

    /**
     * This Method asks the player which map he wants to play with
     */
    public void selectMap(){
        if (firstPlayer){
            boolean valid = false;
            while(!valid) {
                this.printer.printMapOptions ( );
                if (this.parser.parseInt (3) == 0) {
                    valid = true;
                    MapSetUp mapData = new MapSetUp (nickname, SMALL);
                    sendToController (mapData);
                } else if (this.parser.parseInt (3) == 1) {
                    valid = true;
                    MapSetUp mapData = new MapSetUp (nickname, MEDIUM_1);
                    sendToController (mapData);
                } else if (this.parser.parseInt (3) == 2) {
                    valid = true;
                    MapSetUp mapData = new MapSetUp (nickname, MEDIUM_2);
                    sendToController (mapData);
                } else if (this.parser.parseInt (3) == 3) {
                    valid = true;
                    MapSetUp mapData = new MapSetUp (nickname, LARGE);
                    sendToController (mapData);
                } else this.printer.printInvalidInput ( );
            }
        } else {
            printer.print ("The first player in your lobby is choosing the arena. Please wait...");
        }
    }


    /**
     * This method asks the player the action he wants to perform
     */
    public void selectAction(){
        boolean valid = false;
        while(!valid){
            this.printer.printActionOptions();
            if (this.parser.parseInt(3)==0){
                valid = true;
                //TODO move
            }else if(this.parser.parseInt(3)==1){
                valid = true;
                //TODO move and grab
            } else if(this.parser.parseInt(3)==2){
                valid = true;
                //TODO shoot
            } else if(this.parser.parseInt(3)==3){
                valid = true;
                //TODO pass
            } else this.printer.printInvalidInput();
        }
    }

    public void printMap(Square[] arena) {
        //TODO implement
    }
}
