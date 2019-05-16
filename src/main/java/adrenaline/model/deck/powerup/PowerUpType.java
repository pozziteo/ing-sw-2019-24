package adrenaline.model.deck.powerup;


/** Enum which lists all the power-up cards available in the game.
 * Description is the name of the PowerUp, while
 * Ammo.COLOR_AMMO is the ammo bonus you get with a PowerUp.
 * A single type of PowerUp has 3 different cards, one for each color
 */

public enum PowerUpType {

    TAGBACK_GRENADE("Tagback Grenade"),
    TARGETING_SCOPE("Targeting Scope"),
    NEWTON("Newton"),
    TELEPORTER("Teleporter");


    /**
     * description is the name of the PowerUp
     */
    private String description;

    /**
     * Constructor which initialize the enum attributes
     * @param desc is the name of the PowerUp
     */
    PowerUpType(String desc) {
        setDescription(desc);
    }

    /**
     * Setter to initialize the description attribute.
     *
     * @param description which is the PowerUp's name
     */
    private void setDescription(String description) {
        this.description = description;
    }


    /**
     * Getter to obtain the description of the PowerUp
     * @return the name of the PowerUp
     */

    public String getDescription() {
        return description;
    }
}