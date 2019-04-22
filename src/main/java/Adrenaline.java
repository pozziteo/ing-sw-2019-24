import network.Server;
import view.cli.CliParser;
import view.cli.CliUserInterface;

import java.io.IOException;

public class Adrenaline {
    public static void main(String[] args) throws IOException {
        CliParser parser = new CliParser();

        System.out.print("What do you want to run?\n");
        System.out.print("0 - Server\n");
        System.out.print("1 - Client\n");
        if (parser.parseInt(1) == 0) {
            Server.main(args);
        } else {
            System.out.print("Choose your user interface: \n");
            System.out.print("0 - Cli\n");
            System.out.print("1 - GUI\n");
            switch (parser.parseInt(1)) {
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
