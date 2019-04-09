package model.player;

import model.deck.*;
import model.map.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testGiveMarks() throws Exception {
        Map m = Map.getInstance("maps\\smallmap.json");
        Player p1 = new Player("red", m.getSquare(2));
        Player p2 = new Player("blue", m.getSquare(1));
        p1.giveMark (3, p2);
        assertEquals(3, p2.getBoard().getMarksAmountGivenByPlayer (p1));
    }

    @Test
    void testGrabWeapon() {
        Square sp = new SpawnPoint(0, "red", new ArrayList<>());
        Player p = new Player("red", sp);
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
}
