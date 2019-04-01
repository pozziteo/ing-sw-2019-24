package model.deck;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDeck {

    @Test
    public void testWeaponsDeckSize() {
        WeaponsDeckCreator deckCreator = new WeaponsDeckCreator();
        WeaponsDeck d = deckCreator.createDeck();

        ArrayList<String> weapons = new ArrayList<>();
        String weaponsName;

        for (WeaponType w : WeaponType.values ()) {
            weaponsName = w.getDescription ();
            weapons.add(weaponsName);
        }

        System.out.println("List of weapons: " + weapons);

        ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < d.getCards().size(); i++) {
            Weapon weapon = (Weapon) d.getCards().get(i);
            names.add(weapon.getWeaponsName ());
        }
        assertTrue(weapons.containsAll(names));
        assertEquals(d.getCards().size(), 21);
    }

}