package adrenaline.view.cli;

import adrenaline.data.DataForClient;
import adrenaline.data.DataForServer;
import adrenaline.data.data_for_network.AccountSetUp;
import adrenaline.network.ClientInterface;
import adrenaline.network.rmi.client.RmiClient;
import adrenaline.network.socket.client.SocketClient;
import adrenaline.view.UserInterface;

/**
 * Class that implements the user interface using command line.
 */

public class CliUserInterface implements UserInterface {
    private static CliUserInterface instance;
    private CliPrinter printer;
    private CliParser parser;
    private ClientInterface client;

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
}
