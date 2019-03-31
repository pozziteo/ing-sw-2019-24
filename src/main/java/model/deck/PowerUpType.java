package main.java.model.deck;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PowerUpType {

    TAGBACK_GRANADE_B("tagback granade", Ammo.BLUE_AMMO),
    TAGBACK_GRANADE_R("tagback granade", Ammo.RED_AMMO),
    TAGBACK_GRANADE_Y("tagback granade", Ammo.YELLOW_AMMO),
    TARGETING_SCOPE_B("targeting scope", Ammo.BLUE_AMMO),
    TARGETING_SCOPE_R("targeting scope", Ammo.RED_AMMO),
    TARGETING_SCOPE_Y("targeting scope", Ammo.YELLOW_AMMO),
    NEWTON_B("newton", Ammo.BLUE_AMMO),
    NEWTON_R("newton", Ammo.RED_AMMO),
    NEWTON_Y("newton", Ammo.YELLOW_AMMO),
    TELEPORTER_B("teleporter", Ammo.BLUE_AMMO),
    TELEPORTER_R("teleporter", Ammo.RED_AMMO),
    TELEPORTER_Y("teleporter", Ammo.YELLOW_AMMO);

    /** Ammo.COLOR_AMMO is the ammo bonus you get with a PowerUp
     * a single type of PowerUp has 3 different cards, one for color
     */

    /**
     *  bonusAmmo is the ammo's color the powerup gives to the player (only 1 block)
     */
    private List<Ammo> bonusAmmo;

    /**
     * description is the name of the PowerUp
     */
    private String description;


    PowerUpType(String desc, Ammo... ammo) {
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
     * @param ammo is the color of the bonusAmmo
     */
    private void setBonusAmmo(Ammo... ammo) {
        bonusAmmo = new ArrayList<Ammo>();
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
    public List<Ammo> getBonusAmmo() {
        return bonusAmmo;
    }


    /**if something seems wrong, feel free to change it
     */
}