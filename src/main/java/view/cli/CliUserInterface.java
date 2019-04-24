package view.cli;

import network.socket.SocketClient;
import obs.Observer;
import view.UserInterface;
import java.util.List;

public class CliUserInterface implements UserInterface {
    private static CliUserInterface instance;
    private CliPrinter printer;
    private CliParser parser;
    private SocketClient client;
    private List<Observer> observers;

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
            launch();
        }
    }

    public void launch() {
        this.printer.clearScreen ();
        this.printer.printTitle ();
        this.parser.parseEnter ();
        this.printer.clearScreen ();
    }

    public void update(Object object) {

    }

    public void attach(Observer observer) {
        this.observers.add (observer);
    }

    public void detach(Observer observer) {
        this.observers.remove (observer);
    }

    public void inform() {

    }

}
