package model.deck;

/**
 * WeaponType is the enumeration class which lists all the weapon types in the game.
 *
 * Used by Weapon class.
 */

public enum WeaponType {

    LOCK_RIFLE("Lock Rifle"),
    ELECTROSCYTHE("Electroscythe"),
    MACHINE_GUN("Machine Gun"),
    TRACTOR_BEAM("Tractor Beam"),
    THOR("T.H.O.R."),
    VORTEX_CANNON("Vortex Cannon"),
    PLASMA_GUN("Plasma Gun"),
    FURNACE("Furnace"),
    HEATSEEKER("Heatseeker"),
    WHISPER("Whisper"),
    HELLION("Hellion"),
    FLAMETHROWER("Flamethrower"),
    ZX_2("ZX-2"),
    GRENADE_LAUNCHER("Grenade Launcher"),
    SHOTGUN("Shotgun"),
    ROCKET_LAUNCHER("Rocket Launcher"),
    POWER_GLOVE("Power Glove"),
    RAILGUN("Railgun"),
    SHOCKWAVE("Shockwave"),
    CYBERBLADE("Cyberblade"),
    SLEDGEHAMMER("Sledgehammer");

    /**
     * description is the name of the weapon (add some nice description for each weapon?)
     */
    private String description;

    /**
     * Constructor of WeaponType sets the description of each weapon.
     *
     * @param desc which is the description of the weapon passed to the setter method
     */
    WeaponType(String desc) {
        setDescription(desc);
    }

    /**
     * Setter to initialize the description attribute.
     *
     * @param description which is the weapon's name
     */
    private void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter to obtain the description of a weapon.
     *
     * @return the name of the weapon used.
     */
    public String getDescription() {
        return description;
    }
}
