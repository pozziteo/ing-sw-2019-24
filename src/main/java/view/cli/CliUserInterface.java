package view.cli;

import network.Client;
import obs.Observer;
import view.UserInterface;

import java.io.IOException;

public class CliUserInterface implements UserInterface {
    private static CliUserInterface instance;
    private CliPrinter printer;
    private CliParser parser;
    private Client client;

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

    public CliPrinter getPrinter() {
        return this.printer;
    }

    public void establishConnection() {
        this.printer.printConnectionOptions ();
        if (this.parser.parseInt () == 0) {
            //rmi
        } else {
            try {
                this.client = new Client ("localhost", 6666);
                client.startClient ( );
            } catch (IOException e) {
                System.out.println ("error"); //change
            }
        }
    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void attach(Observer observer) {

    }

    @Override
    public void detach(Observer observer) {

    }

    @Override
    public void inform() {

    }

}
