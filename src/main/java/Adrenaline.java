import network.Server;
import view.cli.CliParser;
import view.cli.CliUserInterface;

import java.io.IOException;

public class Adrenaline {
    public static void main(String[] args) throws IOException {
        CliParser parser = new CliParser();

        System.out.print("Press 0 to run the server, 1 to run a client: ");
        if (parser.parseInt() == 0) {
            Server.main(args);
        } else {
            System.out.print("Press 0 to choose Cli as your user interface, 1 for GUI: ");
            switch (parser.parseInt()) {
                case 0:
                    CliUserInterface.getCliInstance ();
                    break;
                case 1:
                    //start gui
                    break;
            }
        }
    }
}
