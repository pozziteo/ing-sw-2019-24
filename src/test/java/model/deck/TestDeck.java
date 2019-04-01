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

    @Test
    public void testPowerUpsDeckSize() {
        PowerUpDeckCreator deckCreator = new PowerUpDeckCreator();
        PowerUpDeck d = deckCreator.createDeck();

        ArrayList<String> powerups = new ArrayList<>();
        String powerUpsName;

        for (PowerUpType p : PowerUpType.values ()) {
            powerUpsName = p.getDescription ();
            powerups.add(powerUpsName);
        }

       // System.out.println("List of powerups: " + powerups);

        ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < d.getCards().size(); i++) {
            PowerUp powerup = (PowerUp) d.getCards().get(i);
            names.add(powerup.getPowerUpsName ());
            System.out.println(powerup.getPowerUpsName () + " " + powerup.getAmmo ().getColor () + ", ");
        }
        assertTrue(powerups.containsAll(names));
        assertEquals(d.getCards().size(), 12);
    }

}