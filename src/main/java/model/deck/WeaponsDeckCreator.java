package model.deck;

public class WeaponsDeckCreator extends DeckCreator {
    /**
     * Calls superclass constructor
     */

    public WeaponsDeckCreator() {
        super();
    }

    /**
     * This method implements the abstract method createDeck() in DeckCreator abstract class
     * @return a deck of weapons
     */

    public WeaponsDeck createDeck() {
        WeaponsDeck weaponsDeck = new WeaponsDeck();

        for (int i = 0; i < 21; i++) {
            Weapon weapon = new Weapon();
            weaponsDeck.addCard(weapon);
        }

        weaponsDeck.deckShuffle ();

        return weaponsDeck;
    }
}