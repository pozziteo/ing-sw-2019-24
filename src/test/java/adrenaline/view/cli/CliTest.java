package adrenaline.view.cli;

import adrenaline.model.Game;
import adrenaline.view.cli.CliPrinter;
import org.junit.jupiter.api.Test;

import java.io.File;

public class CliTest {
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";
    private static final String MEDIUM_1 = PATH + File.separatorChar + "mediummap_1.json";
    private static final String MEDIUM_2 = PATH + File.separatorChar + "mediummap_2.json";
    private static final String LARGE = PATH + File.separatorChar + "largemap.json";

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

        g.setArena (SMALL);
        printer.printArena(g.getMap ().getArena ());
        System.out.print("\n");
        g.setArena (MEDIUM_1);
        printer.printArena(g.getMap ().getArena ());
        System.out.print("\n");
        g.setArena (MEDIUM_2);
        printer.printArena(g.getMap ().getArena ());
        System.out.print("\n");
        g.setArena (LARGE);
        printer.printArena(g.getMap ().getArena ());
    }
}
