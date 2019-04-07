package model.deck;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void weaponsDeckSizeTest() {
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
    void powerUpsDeckSizeTest() {
        PowerUpDeckCreator deckCreator = new PowerUpDeckCreator();
        PowerUpDeck d = deckCreator.createDeck();

        ArrayList<String> powerups = new ArrayList<>();
        String powerUpsName;

        for (PowerUpType p : PowerUpType.values ()) {
            powerUpsName = p.getDescription ();
            powerups.add(powerUpsName);
        }


        ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < d.getCards().size(); i++) {
            PowerUp powerup = (PowerUp) d.getCards().get(i);
            names.add(powerup.getPowerUpsName ());
            System.out.println(powerup.getPowerUpsName () + " " + powerup.getAmmo ().getColor () + ", ");
        }
        assertTrue(powerups.containsAll(names));
        assertEquals(d.getCards().size(), 12);
    }

    @Test
    void tilesDeckSizeTest() {
        TilesDeckCreator deckCreator = new TilesDeckCreator();
        TilesDeck d = deckCreator.createDeck();

        ArrayList<String> tiles = new ArrayList<>();
        String tilesName;

        for (TileFormat t : TileFormat.values ()) {
            tilesName = t.getDescription ();
            tiles.add(tilesName);
        }


        ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < d.getCards().size(); i++) {
            Tile tile = (Tile) d.getCards().get(i);
            names.add(tile.getTileContent ());
        }

        assertTrue(tiles.containsAll(names));
        assertEquals(d.getCards().size(), 36);
    }
}