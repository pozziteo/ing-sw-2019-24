package adrenaline.view.cli;

import adrenaline.model.Game;
import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.WeaponType;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.deck.powerup.PowerUpEffect;
import adrenaline.model.deck.powerup.PowerUpType;
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

    private Ammo ammo = Ammo.RED_AMMO;
    private Ammo ammo1 = Ammo.BLUE_AMMO;
    private Ammo ammo2 = Ammo.YELLOW_AMMO;


    private PowerUpType powerUpType = PowerUpType.TARGETING_SCOPE;
    private PowerUp powerUp = new PowerUp(powerUpType, ammo1);
    private PowerUpType powerUpType1 = PowerUpType.NEWTON;
    private PowerUp powerUp1 = new PowerUp(powerUpType1, ammo);
    private PowerUpType powerUpType2 = PowerUpType.TELEPORTER;
    private PowerUp powerUp2 = new PowerUp(powerUpType2, ammo2);
    private PowerUpType powerUpType3 = PowerUpType.TAGBACK_GRENADE;
    private PowerUp powerUp3 = new PowerUp(powerUpType3, ammo1);
    private ArrayList<PowerUp> pupList = new ArrayList<>(Arrays.asList(powerUp, powerUp1, powerUp2, powerUp3));



    @Test
    public void testPrint() {

        p1.setOwnedWeapons(wList);
        p1.setOwnedPowerUps(pupList);
        printer.printTitle ();
        printer.clearScreen ();
        printer.printConnectionOptions ();
        printer.printMapOptions ();
       /* printer.printInitialSpawnPointOptions (g.getPlayers ().get (0).getOwnedPowerUps ());
        printer.printActionOptions ();
        printer.printWeaponList(p1.getOwnedWeapons());
        printer.printPowerUpList(p1.getOwnedPowerUps());*/
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
