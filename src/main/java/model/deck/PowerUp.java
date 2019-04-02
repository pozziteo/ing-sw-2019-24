package model.deck;

public class PowerUp extends Card {
    private PowerUpType type;
    private Ammo ammoBonus;

    public PowerUp(PowerUpType type) {
        this.type = type;
    }
}