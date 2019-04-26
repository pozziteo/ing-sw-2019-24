package view.cli;

import data.DataForClient;
import data.DataForServer;
import data.data_for_network.AccountSetUp;
import network.ClientInterface;
import network.rmi.client.RmiClient;
import network.socket.client.SocketClient;
import view.UserInterface;

//TODO javadoc

public class CliUserInterface implements UserInterface {
    private static CliUserInterface instance;
    private CliPrinter printer;
    private CliParser parser;
    private ClientInterface client;

    public CliUserInterface() {
        this.printer = new CliPrinter ();
        this.parser = new CliParser ();
    }

    public static CliUserInterface getCliInstance() {
        if (instance == null) {
            instance = new CliUserInterface ( );
            instance.establishConnection ();
        }
        return instance;
    }

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

    public void launchTitleScreen() {
        this.printer.printTitle ();
        this.parser.parseEnter ();
        this.printer.clearScreen ();
    }

    public void updateView(DataForClient data) {
        //needs implementation
    }

    public void sendToController(DataForServer data) {
        client.sendData (data);
    }

    public void setUpAccount() {
        this.printer.printNickname ();
        String nickname = this.parser.parseNickname ();
        AccountSetUp accountData = new AccountSetUp (nickname);
        sendToController (accountData);
    }
}
