package model.deck;

/**
 * PowerUp is a Card which provides a bonus to the player who uses its
 * effect, based on the different PowerUpType
 */
public class PowerUp extends Card {

    /**
     * Type describes the kind of power-up, which defines its effect
     */
    private PowerUpType type;

    /**
     * Constructor which initialize the kinds of a PowerUp
     * @param type is the kind of PowerUp
     */
    public PowerUp(PowerUpType type) {
        this.type = type;
    }

    /**
     * Getter to obtain the name of the PowerUp, to define its effect
     * @return the name of a PowerUp
     */
    public String getPowerUpsName() {
        return this.type.getDescription ();
    }

    /**
     * Getter to obtain the ammo's color provided by the PowerUp
     * @return the ammo's color in the PowerUp card
     */
    public Ammo getAmmo() {
        return this.type.getBonusAmmo ();
    }
}
