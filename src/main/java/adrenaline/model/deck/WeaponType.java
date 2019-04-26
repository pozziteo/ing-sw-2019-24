package adrenaline.model.deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WeaponType is the enumeration class which lists all the weapon types in the game.
 *
 * Used by Weapon class.
 */

public enum WeaponType {

    LOCK_RIFLE("Lock Rifle", Ammo.BLUE_AMMO, Ammo.BLUE_AMMO),
    ELECTROSCYTHE("Electroscythe", Ammo.BLUE_AMMO),
    MACHINE_GUN("Machine Gun", Ammo.BLUE_AMMO, Ammo.RED_AMMO),
    TRACTOR_BEAM("Tractor Beam", Ammo.BLUE_AMMO),
    THOR("T.H.O.R.", Ammo.BLUE_AMMO, Ammo.RED_AMMO),
    VORTEX_CANNON("Vortex Cannon", Ammo.RED_AMMO, Ammo.BLUE_AMMO),
    PLASMA_GUN("Plasma Gun", Ammo.BLUE_AMMO, Ammo.YELLOW_AMMO),
    FURNACE("Furnace", Ammo.RED_AMMO, Ammo.BLUE_AMMO),
    HEATSEEKER("Heatseeker", Ammo.RED_AMMO, Ammo.RED_AMMO, Ammo.YELLOW_AMMO),
    WHISPER("Whisper", Ammo.BLUE_AMMO, Ammo.BLUE_AMMO, Ammo.YELLOW_AMMO),
    HELLION("Hellion", Ammo.RED_AMMO, Ammo.YELLOW_AMMO),
    FLAMETHROWER("Flamethrower", Ammo.RED_AMMO),
    ZX_2("ZX-2", Ammo.YELLOW_AMMO, Ammo.RED_AMMO),
    GRENADE_LAUNCHER("Grenade Launcher", Ammo.RED_AMMO),
    SHOTGUN("Shotgun", Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO),
    ROCKET_LAUNCHER("Rocket Launcher", Ammo.RED_AMMO, Ammo.RED_AMMO),
    POWER_GLOVE("Power Glove", Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO),
    RAILGUN("Railgun", Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO),
    SHOCKWAVE("Shockwave", Ammo.YELLOW_AMMO),
    CYBERBLADE("Cyberblade", Ammo.YELLOW_AMMO, Ammo.RED_AMMO),
    SLEDGEHAMMER("Sledgehammer", Ammo.YELLOW_AMMO);

    /**
     * description is the name of the weapon (add some nice description for each weapon?)
     */
    private String description;

    /**
     * reloadingAmmo is the cost in ammo to reload a specific weapon.
     */
    private List<Ammo> reloadingAmmo;


    /**
     * Constructor which initialize the description of the weapon and its cost in ammo for reloading.
     *
     * @param desc which is the name of the weapon.
     * @param ammo which is the cost in ammo for reloading the weapon.
     */
    WeaponType(String desc, Ammo... ammo) {
        setDescription(desc);
        setReloadingAmmo(ammo);
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
     * Setter to initialize the cost in ammo to reload a weapon
     *
     * @param ammo which is the cost to reload the weapon
     */
    private void setReloadingAmmo(Ammo... ammo) {
        reloadingAmmo = new ArrayList<>();
        reloadingAmmo.addAll(Arrays.asList(ammo));
    }

    /**
     * Getter to obtain the description of a weapon.
     *
     * @return the name of the weapon used.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter to obtain the cost in ammo to reload a weapon
     *
     * @return the cost to reload the weapon
     */
    public List<Ammo> getReloadingAmmo() {
        return reloadingAmmo;
    }

    /**
     * Getter to obtain the cost in ammo to grab a weapon from the game board,
     * which is the reload cost except the "top" ammo represented on the weapon's card, that
     * is the first ammo memorized in the reloadingAmmo attribute.
     *
     * @return the cost to grab a weapon from the game board, that is the reloadingAmmo attribute
     *          except its first element.
     */
    public List<Ammo> getGrabbingCost() {
        ArrayList<Ammo> grabbingCost = new ArrayList<>(reloadingAmmo);
        grabbingCost.remove(0);
        return grabbingCost;
    }
}


