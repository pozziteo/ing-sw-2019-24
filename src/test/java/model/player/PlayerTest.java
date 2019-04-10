package model.player;

import model.Game;
import model.deck.*;
import model.map.*;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Game g = new Game(3);

    @Test
    void testGiveMarks() throws Exception {
        Map m = new ArenaBuilder().createMap();
        Player p1 = new Player(g,"red");
        Player p2 = new Player(g, "blue");

        p1.setPosition(m.getSquare(2));
        p2.setPosition(m.getSquare(1));

        p1.giveMark (3, p2);
        assertEquals(3, p2.getBoard().getMarksAmountGivenByPlayer (p1));
    }

    @Test
    void testGrabWeapon() {
        Square sp = new SpawnPoint(0, "red", new ArrayList<>());
        Player p = new Player(g,"red");
        p.setPosition(sp);

        WeaponsDeckCreator deckCreator = new WeaponsDeckCreator();
        WeaponsDeck d = deckCreator.createDeck();
        Weapon w = (Weapon) d.drawCard ();
        ((SpawnPoint) sp).setWeapons ((Weapon) d.drawCard ());
        ((SpawnPoint) sp).setWeapons (w);
        ((SpawnPoint) sp).setWeapons ((Weapon) d.drawCard ());
        for (int i = 0; i < 3; i++) {
            System.out.println (i+1 + ": " + ((SpawnPoint) sp).getWeapons ()[i].getWeaponsName ());
        }
        p.grabWeapon (w);
        System.out.println(p.getOwnedWeapons ().get(0).getWeaponsName ());
        for (int i = 0; i < 3; i++) {
            if (((SpawnPoint) sp).getWeapons()[i] == null) {
                System.out.println (i + 1 + ": " + "element has been removed");
            } else {
                System.out.println (i + 1 + ": " + ((SpawnPoint) sp).getWeapons ( )[i].getWeaponsName ( ));
            }
        }
    }

    @Test
    public void testPlayersInSameRoom() throws FileNotFoundException {
        Game game = new Game(3);
        Player p1 = new Player(game, "red");
        Player p2 = new Player(game, "blue");

        p1.setPosition(game.getArena().getSquare(0));
        p2.setPosition(game.getArena().getSquare(1));

        assertTrue(p1.isInTheSameRoom (p2));
    }

    @Test
    public void testPlayerCanSee() throws FileNotFoundException {
        Game game = new Game(3);
        Player p1 = new Player(game, "red");
        Player p2 = new Player(game, "blue");

        p1.setPosition(game.getArena().getSquare(0));
        p2.setPosition(game.getArena().getSquare(6));

        assertTrue(p1.canSee (p2));
    }

    @Test
    public void testPlayerGrabsTile() throws FileNotFoundException {
        Game game = new Game(3);
        Player p = new Player(game, "red");
        p.setPosition(game.getArena().getSquare(0));

        game.setTile (p.getPosition ());
        Tile t = p.getPosition ().getPlacedTile ();
        System.out.println(t.getFormat ().getDescription ());
        p.grabTile (t);
        if (t.getFormat ().isPowerUpIsPresent ()) {
            for (int i = 0; i < 2; i++) {
                System.out.println (t.getTileContent ().get(i).getColor () + " " + p.getBoard ( ).getAmountOfAmmo (t.getTileContent ( ).get (i)));
            }
        } else {
            for (int i = 0; i < 3; i++) {
                System.out.println (t.getTileContent().get(i).getColor () + " " + p.getBoard ( ).getAmountOfAmmo (t.getTileContent ( ).get (i)));
            }
        }
    }
}
