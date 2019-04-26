package adrenaline.model.deck;

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

        for (WeaponType type : WeaponType.values()) {
            Weapon weapon = new Weapon(type);
            weaponsDeck.addCard(weapon);
        }

        weaponsDeck.deckShuffle ();

        return weaponsDeck;
    }
}