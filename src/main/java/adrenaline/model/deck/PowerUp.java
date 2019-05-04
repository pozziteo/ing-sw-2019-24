package adrenaline.model.deck;

/**
 * PowerUp is a Card which provides a bonus to the player who uses its
 * effect, based on the different PowerUpType
 */
public class PowerUp extends Card {

    /**
     * Type describes the kind of power-up, which defines its effect
     */
    private PowerUpType type;

    protected PowerUp() {
        super();
    }

    /**
     * Constructor which initialize the kinds of a PowerUp
     * @param type is the kind of PowerUp
     */
    public PowerUp(PowerUpType type) {
        this.type = type;
    }

    /**
     * Getter method to obtain the type of power up
     * @return type is the kind of PowerUp
     */

    public PowerUpType getType() {
        return this.type;
    }

    /**
     * Getter to obtain the name of the PowerUp, to define its effect
     * @return the name of a PowerUp
     */
    public String getPowerUpsName() {
        return this.getType ().getDescription ();
    }

    /**
     * Getter to obtain the ammo's color provided by the PowerUp
     * @return the ammo's color in the PowerUp card
     */
    public Ammo getAmmo() {
        return this.getType().getBonusAmmo ();
    }
}