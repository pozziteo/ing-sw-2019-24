package adrenaline.view.cli;

import adrenaline.model.Game;
import adrenaline.view.cli.CliPrinter;
import org.junit.jupiter.api.Test;

public class CliTest {
    String[] playerNames = {"luca", "matteo", "sara"};
    private Game g = new Game (playerNames);
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
