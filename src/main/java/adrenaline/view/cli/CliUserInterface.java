package adrenaline.view.cli;

import adrenaline.data.DataForClient;
import adrenaline.data.DataForServer;
import adrenaline.data.data_for_game.MapSetUp;
import adrenaline.data.data_for_network.AccountSetUp;
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

    /**
     * Method to connect this client to the server based on the connection type chosen.
     */

    private void establishConnection() {
        this.printer.printConnectionOptions ();
        if (this.parser.parseInt (1) == 0) {
            this.client = new RmiClient (this);
            client.connectToServer ();
        } else {
            this.client = new SocketClient ("localhost", 6666, this);
            client.connectToServer ();
        }
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

    public void updateView(DataForClient data) {
        //this.printer.printData();
    }

    public void setUpAccount() {
        this.printer.printNickname ();
        String nickname = this.parser.parseNickname ();
        AccountSetUp accountData = new AccountSetUp (nickname);
        sendToController (accountData);
    }


    /**
     * This Method asks the player which map he wants to play with
     */
    private void mapSelector(){
        this.printer.printMapOptions();
        if (this.parser.parseInt(3)==0){
            MapSetUp mapData = new MapSetUp (SMALL);
            sendToController (mapData);
        }else if (this.parser.parseInt(3)==1){
            MapSetUp mapData = new MapSetUp (MEDIUM_1);
            sendToController (mapData);
        }else if (this.parser.parseInt(3)==2){
            MapSetUp mapData = new MapSetUp (MEDIUM_2);
            sendToController (mapData);
        }else if (this.parser.parseInt(3)==3){
            MapSetUp mapData = new MapSetUp (LARGE);
            sendToController (mapData);
        }
        else this.printer.printInvalidInput();

    }


    /**
     * This method asks the player the action he wants to perform
     */
    private void actionSelector(){
        boolean valid = false;
        while(!valid){
            this.printer.printActionOptions();
            if (this.parser.parseInt(3)==0){
                valid=true;
                //TODO move
            }else if(this.parser.parseInt(3)==1){
                valid=true;
                //TODO move and grab
            } else if(this.parser.parseInt(3)==2){
                valid=true;
                //TODO shoot
            } else if(this.parser.parseInt(3)==3){
                valid=true;
                //TODO pass
            } else{
                this.printer.printInvalidInput();
            }
        }
    }
}
