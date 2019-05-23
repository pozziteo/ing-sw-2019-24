package adrenaline.view.cli;

import adrenaline.model.Game;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.WeaponType;
import adrenaline.model.player.Player;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CliTest {
    String[] playerNames = {"luca", "matteo", "sara"};
    private Game g = new Game (playerNames);
    private Player p1 = g.getPlayers ().get(0);
    private Player p2 = g.getPlayers ().get(1);
    private CliPrinter printer = new CliPrinter();
    private WeaponType wt = WeaponType.GRENADE_LAUNCHER;
    private Weapon w = new Weapon(wt);
    private WeaponType wt1 = WeaponType.MACHINE_GUN;
    private Weapon w1 = new Weapon(wt1);
    private ArrayList<Weapon> wList = new ArrayList<>(Arrays.asList(w, w1));
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";


    @Test
    public void testPrint() {

        p1.setOwnedWeapons(wList);
        printer.printTitle ();
        printer.clearScreen ();
        printer.printConnectionOptions ();
        printer.printMapOptions ();
        printer.printInitialSpawnPointOptions (g.getPlayers ().get (0).getOwnedPowerUps ());
        printer.printSetUpComplete ();
        printer.printActionOptions ();
        printer.printRanking (g.getRanking());
        printer.printWeaponList(p1.getOwnedWeapons());
    }

    @Test
    public void testPrintMap(){
        printer.printSmallMap ();
        System.out.print("\n");
        printer.printMedium2Map();
        System.out.print("\n");
        printer.printLargeMap();
        System.out.println("\n");
        printer.printMedium1Map();
    }
}
