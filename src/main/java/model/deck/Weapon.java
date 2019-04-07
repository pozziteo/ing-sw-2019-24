package model.deck;

/**
 * Weapon is a Card which contains the description about a single
 * weapon available in the game and its effects
 */
public class Weapon extends Card {

    /**
     * Type is the kind of weapon, which defines its effects
     */
    private WeaponType type;

    /**
     * Constructor which initialize the type of weapon
     * @param type is the type of a weapon
     */
    public Weapon(WeaponType type) {
        this.type = type;
    }

    /**
     * Getter to obtain the kind of weapon to know its effects
     * @return the type of a weapon
     */
    public String getWeaponsName() {
        return this.type.getDescription ();
    }
}
