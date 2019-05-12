package adrenaline.model.deck;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * WeaponType is the enumeration class which lists all the weapon types in the game.
 *
 * Used by Weapon class.
 */

public enum WeaponType {

    LOCK_RIFLE("Lock Rifle", Ammo.BLUE_AMMO, Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "lockrifle.json";
        }
    },
    ELECTROSCYTHE("Electroscythe", Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "electroscythe.json";
        }
    },
    MACHINE_GUN("Machine Gun", Ammo.BLUE_AMMO, Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "machinegun.json";
        }
    },
    TRACTOR_BEAM("Tractor Beam", Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "tractorbeam.json";
        }
    },
    THOR("T.H.O.R.", Ammo.BLUE_AMMO, Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "thor.json";
        }
    },
    VORTEX_CANNON("Vortex Cannon", Ammo.RED_AMMO, Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "vortexcannon.json";
        }
    },
    PLASMA_GUN("Plasma Gun", Ammo.BLUE_AMMO, Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "plasmagun.json";
        }
    },
    FURNACE("Furnace", Ammo.RED_AMMO, Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "furnace.json";
        }
    },
    HEATSEEKER("Heatseeker", Ammo.RED_AMMO, Ammo.RED_AMMO, Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "heatseeker.json";
        }
    },
    WHISPER("Whisper", Ammo.BLUE_AMMO, Ammo.BLUE_AMMO, Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "whisper.json";
        }
    },
    HELLION("Hellion", Ammo.RED_AMMO, Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "hellion.json";
        }
    },
    FLAMETHROWER("Flamethrower", Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "flamethrower.json";
        }
    },
    ZX_2("ZX-2", Ammo.YELLOW_AMMO, Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "zx2.json";
        }
    },
    GRENADE_LAUNCHER("Grenade Launcher", Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "grenadelauncher.json";
        }
    },
    SHOTGUN("Shotgun", Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "shotgun.json";
        }
    },
    ROCKET_LAUNCHER("Rocket Launcher", Ammo.RED_AMMO, Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "rocketlauncher.json";
        }
    },
    POWER_GLOVE("Power Glove", Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "powerglove.json";
        }
    },
    RAILGUN("Railgun", Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "railgun.json";
        }
    },
    SHOCKWAVE("Shockwave", Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "shockwave.json";
        }
    },
    CYBERBLADE("Cyberblade", Ammo.YELLOW_AMMO, Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "cyberblade.json";
        }
    },
    SLEDGEHAMMER("Sledgehammer", Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "sledgehammer.json";
        }
    };

    private static final String WEAPONS_PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "weapons" + File.separatorChar;

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
        this.description = desc;
        reloadingAmmo = new ArrayList<>();
        reloadingAmmo.addAll(Arrays.asList(ammo));
    }

    public String getPath() {
        return WEAPONS_PATH;
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


