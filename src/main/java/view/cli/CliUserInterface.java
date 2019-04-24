package view.cli;

import network.socket.SocketClient;
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
        this.printer.printConnectionOptions ();
        if (this.parser.parseInt (1) == 0) {
            //rmi
        } else {
            this.client = new SocketClient ("localhost", 6666, this);
            client.connect ();
        }
    }

    public void launch() {
        this.printer.clearScreen ();
        this.printer.printTitle ();
        this.parser.parseEnter ();
        this.printer.clearScreen ();
    }
}
