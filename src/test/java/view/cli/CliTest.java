package view.cli;

import model.Game;
import org.junit.jupiter.api.Test;

public class CliTest {
    private Game g = new Game (5);
    private CliPrinter printer = new CliPrinter();

    @Test
    public void testPrint() {
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
