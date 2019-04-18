package view.cli;

import model.Game;
import org.junit.jupiter.api.Test;

public class CliTest {
    Game g = new Game (5);
    private CliUserInterface cli = CliUserInterface.getCliInstance ();

    @Test
    public void testPrint() {
        CliPrinter printer = this.cli.getPrinter ();
        printer.printTitle ();
        printer.clearScreen ();
        printer.printConnectionOptions ();
        printer.printMapOptions ();
        printer.printInitialSpawnPointOptions (g.getPlayers ().get (0).getOwnedPowerUps ());
        printer.printSetUpComplete ();
        printer.printActionOptions ();
        printer.printRanking (g.getRanking());
    }
}
