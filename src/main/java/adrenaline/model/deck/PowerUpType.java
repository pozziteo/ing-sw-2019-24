package adrenaline.model.deck;


/** Enum which lists all the power-up cards available in the game.
 * Description is the name of the PowerUp, while
 * Ammo.COLOR_AMMO is the ammo bonus you get with a PowerUp.
 * A single type of PowerUp has 3 different cards, one for each color
 */

public enum PowerUpType {

    TAGBACK_GRENADE_B("Tagback Grenade - Blue Ammo", Ammo.BLUE_AMMO),
    TAGBACK_GRENADE_R("Tagback Grenade - Red Ammo", Ammo.RED_AMMO),
    TAGBACK_GRENADE_Y("Tagback Grenade - Yellow Ammo", Ammo.YELLOW_AMMO),
    TARGETING_SCOPE_B("Targeting Scope - Blue Ammo", Ammo.BLUE_AMMO),
    TARGETING_SCOPE_R("Targeting Scope - Red Ammo", Ammo.RED_AMMO),
    TARGETING_SCOPE_Y("Targeting Scope - Yellow Ammo", Ammo.YELLOW_AMMO),
    NEWTON_B("Newton - Blue Ammo", Ammo.BLUE_AMMO),
    NEWTON_R("Newton - Red Ammo", Ammo.RED_AMMO),
    NEWTON_Y("Newton - Yellow Ammo", Ammo.YELLOW_AMMO),
    TELEPORTER_B("Teleporter - Blue Ammo", Ammo.BLUE_AMMO),
    TELEPORTER_R("Teleporter - Red Ammo", Ammo.RED_AMMO),
    TELEPORTER_Y("Teleporter - Yellow Ammo", Ammo.YELLOW_AMMO);

    /**
     *  bonusAmmo is the ammo's color the powerup gives to the player (only 1 block)
     */
    private Ammo bonusAmmo;

    /**
     * description is the name of the PowerUp
     */
    private String description;

    /**
     * Constructor which initialize the enum attributes
     * @param desc is the name of the PowerUp
     * @param ammo is the color of the bonus ammo provided by the PowerUp
     */
    PowerUpType(String desc, Ammo ammo) {
        setDescription(desc);
        setBonusAmmo(ammo);
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
     *
     * @param bonusAmmo is the color of the bonusAmmo
     */
    private void setBonusAmmo(Ammo bonusAmmo) {
        this.bonusAmmo = bonusAmmo;
        //ammo's color
    }


    /**
     * Getter to obtain the description of the PowerUp
     * @return the name of the PowerUp
     */

    public String getDescription() {
        return description;
    }

    /**
     * Getter to obtain the color of the bonusAmmo
     *
     * @return the cost to reload the weapon
     */
    public Ammo getBonusAmmo() {
        return bonusAmmo;
    }

    /**
     * Method to obtain the power up's color
     * @return the color of the power up (i.e. the color of the ammo or the spawn point
     *      to be regenerated
     */

    public String getColor() {
        return this.bonusAmmo.getColor ();
    }
}