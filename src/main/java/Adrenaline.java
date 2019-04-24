import view.cli.CliParser;
import view.cli.CliUserInterface;

public class Adrenaline {
    public static void main(String[] args) {
        CliParser parser = new CliParser();

        System.out.print("Choose your user interface: \n");
        System.out.print("0 - Cli\n");
        System.out.print("1 - GUI\n");
        if (parser.parseInt(1) == 0) {
            CliUserInterface.getCliInstance (); }
        else {
            //start gui
        }
    }
}

