package view.cli;

import data.DataForClient;
import network.socket.client.SocketClient;
import view.UserInterface;

public class CliUserInterface implements UserInterface {
    private static CliUserInterface instance;
    private CliPrinter printer;
    private CliParser parser;
    private SocketClient client;

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

    public void establishConnection() {
        this.printer.printNickname();
        String nickname = this.parser.parseNickname();
        this.printer.printConnectionOptions ();
        if (this.parser.parseInt (1) == 0) {
            //rmi
        } else {
            this.client = new SocketClient (nickname, "localhost", 6666, this);
            client.connectToServer ();
        }
    }

    public void launch() {
        this.printer.clearScreen ();
        this.printer.printTitle ();
        this.parser.parseEnter ();
        this.printer.clearScreen ();
    }

    public void updateView(DataForClient data) {
        //needs implementation
    }
}
