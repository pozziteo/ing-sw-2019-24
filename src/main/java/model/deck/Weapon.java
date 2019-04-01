package model.deck;

public class Weapon extends Card {
    private WeaponType type;

    public Weapon(WeaponType type) {
        this.type = type;
    }

    public String getWeaponsName() {
        return this.type.getDescription ();
    }
}
