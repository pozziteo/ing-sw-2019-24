package view.cli;

import view.UserInterface;

public class CliUserInterface implements UserInterface {
    private static CliUserInterface instance;
    private CliPrinter printer;
    private CliParser parser;

    public CliUserInterface() {
        this.printer = new CliPrinter ();
        this.parser = new CliParser ();
    }

    public static CliUserInterface getCliInstance() {
        if (instance == null) {
            instance = new CliUserInterface ( );
        }
        return instance;
    }

    public CliPrinter getPrinter() {
        return this.printer;
    }

    @Override
    public void callController(/*insert param*/) {

    }

    @Override
    public void getState(/*insert param*/) {

    }
}
