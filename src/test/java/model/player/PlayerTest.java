package model.player;

import model.Game;
import model.deck.*;
import model.map.*;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Game game = new Game(3);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);
    private Player p3 = game.getPlayers ().get(2);;

    @Test
    void testGiveMarks() throws Exception {
        p1.setPosition(game.getArena ().getSquare(2));
        p2.setPosition(game.getArena ().getSquare(1));

        p1.giveMark (3, p2);
        assertEquals(3, p2.getBoard().getMarksAmountGivenByPlayer (p1));
    }

    @Test
    void testGrabWeapon() {
        SpawnPoint sp = new SpawnPoint(0, "red", new ArrayList<>());
        p1.setPosition(sp);

        WeaponsDeckCreator deckCreator = new WeaponsDeckCreator();
        WeaponsDeck d = deckCreator.createDeck();
        Weapon w = (Weapon) d.drawCard ();
        sp.setWeapons ((Weapon) d.drawCard ());
        sp.setWeapons (w);
        sp.setWeapons ((Weapon) d.drawCard ());

        p1.grabWeapon (w);

        assertTrue(p1.getOwnedWeapons ().get(0).getWeaponsName ().equals(w.getWeaponsName ()));
    }

    @Test
    public void testPlayersInSameRoom() {
        p1.setPosition(game.getArena().getSquare(0));
        p2.setPosition(game.getArena().getSquare(1));

        assertTrue(p1.isInTheSameRoom (p2));
    }

    @Test
    public void testPlayerCanSee() {
        p1.setPosition(game.getArena().getSquare(0));
        p2.setPosition(game.getArena().getSquare(6));

        assertTrue(p1.canSee (p2));
    }

    @Test
    public void testPlayerGrabsTile() {
        p1.setPosition(game.getArena().getSquare(0));

        game.setTileOnSquare ((NormalSquare) (p1.getPosition ()));
        Tile t = ((NormalSquare) p1.getPosition()).getPlacedTile ();
        String format = t.getFormat ().getDescription ();
        p1.grabTile (t);
        switch (format) {
            case "PowerUp YELLOW_AMMO YELLOW_AMMO":
                assertEquals(3, p1.getBoard ( ).getAmountOfAmmo (Ammo.YELLOW_AMMO));
                break;
            case "PowerUp RED_AMMO RED_AMMO":
                assertEquals(3, p1.getBoard ( ).getAmountOfAmmo (Ammo.RED_AMMO));
                break;
            case "PowerUp BLUE_AMMO BLUE_AMMO":
                assertEquals(3, p1.getBoard ( ).getAmountOfAmmo (Ammo.BLUE_AMMO));
                break;
            case "PowerUp YELLOW_AMMO RED_AMMO":
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.YELLOW_AMMO));
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.RED_AMMO));
                break;
            case "PowerUp YELLOW_AMMO BLUE_AMMO":
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.YELLOW_AMMO));
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.BLUE_AMMO));
                break;
            case "PowerUp RED_AMMO BLUE_AMMO":
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.RED_AMMO));
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.BLUE_AMMO));
                break;
            case "YELLOW_AMMO BLUE_AMMO BLUE_AMMO":
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.YELLOW_AMMO));
                assertEquals(3, p1.getBoard ( ).getAmountOfAmmo (Ammo.BLUE_AMMO));
                break;
            case "YELLOW_AMMO RED_AMMO RED_AMMO":
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.YELLOW_AMMO));
                assertEquals(3, p1.getBoard ( ).getAmountOfAmmo (Ammo.RED_AMMO));
                break;
            case "RED_AMMO BLUE_AMMO BLUE_AMMO":
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.RED_AMMO));
                assertEquals(3, p1.getBoard ( ).getAmountOfAmmo (Ammo.BLUE_AMMO));
                break;
            case "RED_AMMO YELLOW_AMMO YELLOW_AMMO":
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.RED_AMMO));
                assertEquals(3, p1.getBoard ( ).getAmountOfAmmo (Ammo.YELLOW_AMMO));
                break;
            case "BLUE_AMMO YELLOW_AMMO YELLOW_AMMO":
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.BLUE_AMMO));
                assertEquals(3, p1.getBoard ( ).getAmountOfAmmo (Ammo.YELLOW_AMMO));
                break;
            case "BLUE_AMMO RED_AMMO RED_AMMO":
                assertEquals(2, p1.getBoard ( ).getAmountOfAmmo (Ammo.BLUE_AMMO));
                assertEquals(3, p1.getBoard ( ).getAmountOfAmmo (Ammo.RED_AMMO));
                break;
        }

    }
}
