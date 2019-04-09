package model.deck;

/**
 * Weapon is a Card which contains the description about a single
 * weapon available in the game and its effects
 */
public class Weapon extends Card {

    private int damage;
    private int mark;
    private int extraDamage;
    private int extraDamage2;
    private int extraMark;
    private int attackerBasicMovements;
    private int attackerBonusMovements1;
    private int attackerBonusMovements2;
    private int victimBasicMovements;
    private int victimBonusMovements1;
    private int victimBonusMovements2;
    private Ammo colorAmmo1;
    private Ammo colorAmmo2;
    private Ammo colorAmmo3;
    private Ammo colorBonusAmmo1;
    private Ammo colorBonusAmmo2;
    private int requiredAmmo;
    private int requiredBonusAmmo;
    private int requiredSecondBonusAmmo;

    /**
     * Type is the kind of weapon, which defines its effects
     */
    private WeaponType type;

    protected Weapon() {
        super();
    }

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
