package model.deck;

public class PowerUp extends Card {
    private PowerUpType type;
    private Ammo ammoBonus;

    public PowerUp(PowerUpType type) {
        this.type = type;
    }

    public String getPowerUpsName() {
        return this.type.getDescription ();
    }

    public Ammo getAmmo() {
        return this.type.getBonusAmmo ();
    }
}
