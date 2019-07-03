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

    LOCK_RIFLE("Lock Rifle", "basic effect: Deal 2 damage and 1 mark to 1 target you can see.\n" +
            "with second lock: Deal 1 mark to a different target you can see.\n", Ammo.BLUE_AMMO, Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "lockrifle.json";
        }
    },
    ELECTROSCYTHE("Electroscythe", "basic mode: Deal 1 damage to every other player on your square.\n" +
            "in reaper mode: Deal 2 damage to every other player on your square.\n",  Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "electroscythe.json";
        }
    },
    MACHINE_GUN("Machine Gun", "basic effect: Choose 1 or 2 targets you can see and deal\n" +
            "1 damage to each.\n" +
            "with focus shot: Deal 1 additional damage to one of those\n" +
            "targets.\n" +
            "with turret tripod: Deal 1 additional damage to the other\n" +
            "of those targets and/or deal 1 damage to a different target\n" +
            "you can see.\n" +
            "Notes: If you deal both additional points of damage,\n" +
            "they must be dealt to 2 different targets. If you see only\n" +
            "2 targets, you deal 2 to each if you use both optional\n" +
            "effects. If you use the basic effect on only 1 target, you can\n" +
            "still use the the turret tripod to give it 1 additional damage.\n", Ammo.BLUE_AMMO, Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "machinegun.json";
        }
    },
    TRACTOR_BEAM("Tractor Beam", "basic mode: MoveAction a target 0, 1, or 2 squares to a square\n" +
            "you can see, and give it 1 damage.\n" +
            "in punisher mode: Choose a target 0, 1, or 2 moves away\n" +
            "from you. MoveAction the target to your square\n" +
            "and deal 3 damage to it.\n" +
            "Notes: You can move a target even if you can't see it.\n" +
            "The target ends up in a place where you can see and\n" +
            "damage it. The moves do not have to be in the same direction.\n", Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "tractorbeam.json";
        }
    },
    THOR("T.H.O.R.", "basic effect: Deal 2 damage to 1 target you can see.\n" +
            "with chain reaction: Deal 1 damage to a second target that\n" +
            "your first target can see.\n" +
            "with high voltage: Deal 2 damage to a third target that\n" +
            "your second target can see. You cannot use this effect\n" +
            "unless you first use the chain reaction.\n" +
            "Notes: This card constrains the order in which you can use\n" +
            "its effects. (Most cards don't.) Also note that each target must be a different player.\n", Ammo.BLUE_AMMO, Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "thor.json";
        }
    },
    VORTEX_CANNON("Vortex Cannon", "basic effect: Choose a square you can see, but not your\n" +
            "square. Call it \"the vortex\". Choose a target on the vortex\n" +
            "or 1 move away from it. MoveAction it onto the vortex and give it\n" +
            "2 damage.\n" +
            "with black hole: Choose up to 2 other targets on the\n" +
            "vortex or 1 move away from it. MoveAction them onto the vortex\n" +
            "and give them each 1 damage.\n" +
            "Notes: The 3 targets must be different, but some might\n" +
            "start on the same square. It is legal to choose targets on\n" +
            "your square, on the vortex, or even on squares you can't\n" +
            "see. They all end up on the vortex.\n", Ammo.RED_AMMO, Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "vortexcannon.json";
        }
    },
    PLASMA_GUN("Plasma Gun", "basic effect: Deal 2 damage to 1 target you can see.\n" +
            "with phase glide: MoveAction 1 or 2 squares. This effect can be\n" +
            "used either before or after the basic effect.\n" +
            "with charged shot: Deal 1 additional damage to your\n" +
            "target.\n" +
            "Notes: The two moves have no ammo cost. You don't have\n" +
            "to be able to see your target when you play the card.\n" +
            "For example, you can move 2 squares and shoot a target\n" +
            "you now see. You cannot use 1 move before shooting and 1 move after.\n", Ammo.BLUE_AMMO, Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "plasmagun.json";
        }
    },
    FURNACE("Furnace", "basic mode: Choose a room you can see, but not the room\n" +
            "you are in. Deal 1 damage to everyone in that room.\n" +
            "in cozy fire mode: Choose a square exactly one move\n" +
            "away. Deal 1 damage and 1 mark to everyone on that square.\n", Ammo.RED_AMMO, Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "furnace.json";
        }
    },
    HEATSEEKER("Heatseeker", "effect: Choose 1 target you cannot see and deal 3 damage\n" +
            "to it.\n" +
            "Notes: Yes, this can only hit targets you cannot see.\n", Ammo.RED_AMMO, Ammo.RED_AMMO, Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "heatseeker.json";
        }
    },
    WHISPER("Whisper", "effect: Deal 3 damage and 1 mark to 1 target you can see.\n" +
            "Your target must be at least 2 moves away from you.\n" +
            "Notes: For example, in the 2-by-2 room, you cannot shoot\n" +
            "a target on an adjacent square, but you can shoot a target\n" +
            "on the diagonal. If you are beside a door, you can't shoot\n" +
            "a target on the other side of the door, but you can shoot\n" +
            "a target on a different square of that room.\n", Ammo.BLUE_AMMO, Ammo.BLUE_AMMO, Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "whisper.json";
        }
    },
    HELLION("Hellion", "basic mode: Deal 1 damage to 1 target you can see at least\n" +
            "1 move away. Then give 1 mark to that target and everyone\n" +
            "else on that square.\n" +
            "in nano-tracer mode: Deal 1 damage to 1 target you can\n" +
            "see at least 1 move away. Then give 2 marks to that target\n" +
            "and everyone else on that square.\n", Ammo.RED_AMMO, Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "hellion.json";
        }
    },
    FLAMETHROWER("Flamethrower", "basic mode: Choose a square 1 move away and possibly a second square\n" +
            "1 more move away in the same direction. On each square, you may\n" +
            "choose 1 target and give it 1 damage.\n" +
            "in barbecue mode: Choose 2 squares as above. Deal 2 damage to\n" +
            "everyone on the first square and 1 damage to everyone on the second\n" +
            "square.\n" +
            "Notes: This weapon cannot damage anyone in your square. However,\n" +
            "it can sometimes damage a target you can't see – the flame won't go\n" +
            "through walls, but it will go through doors. Think of it as a straight-line\n" +
            "blast of flame that can travel 2 squares in a cardinal direction.\n",  Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "flamethrower.json";
        }
    },
    ZX_2("ZX-2", "basic mode: Deal 1 damage and 2 marks to\n" +
            "1 target you can see.\n" +
            "in scanner mode: Choose up to 3 targets you\n" +
            "can see and deal 1 mark to each.\n" +
            "Notes: Remember that the 3 targets can be in 3 different rooms.\n", Ammo.YELLOW_AMMO, Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "zx2.json";
        }
    },
    GRENADE_LAUNCHER("Grenade Launcher", "basic effect: Deal 1 damage to 1 target you can see. Then you may move\n" +
            "the target 1 square.\n" +
            "with extra grenade: Deal 1 damage to every player on a square you can\n" +
            "see. You can use this before or after the basic effect's move.\n" +
            "Notes: For example, you can shoot a target, move it onto a square with\n" +
            "other targets, then damage everyone including the first target.\n" +
            "Or you can deal 2 to a main target, 1 to everyone else on that square,\n" +
            "then move the main target. Or you can deal 1 to an isolated target and\n" +
            "1 to everyone on a different square. If you target your own square,\n" +
            "you will not be moved or damaged.\n", Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "grenadelauncher.json";
        }
    },
    SHOTGUN("Shotgun", "basic mode: Deal 3 damage to 1 target on\n" +
            "your square. If you want, you may then move the target 1 square.\n" +
            "in long barrel mode: Deal 2 damage to\n" +
            "1 target on any square exactly one move away.\n", Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "shotgun.json";
        }
    },
    ROCKET_LAUNCHER("Rocket Launcher", "basic effect: Deal 2 damage to 1 target you can see that is not on your\n" +
            "square. Then you may move the target 1 square.\n" +
            "with rocket jump: MoveAction 1 or 2 squares. This effect can be used either\n" +
            "before or after the basic effect.\n" +
            "with fragmenting warhead: During the basic effect, deal 1 damage to\n" +
            "every player on your target's original square – including the target,\n" +
            "even if you move it.\n" +
            "Notes: If you use the rocket jump before the basic effect, you consider\n" +
            "only your new square when determining if a target is legal. You can\n" +
            "even move off a square so you can shoot someone on it. If you use the\n" +
            "fragmenting warhead, you deal damage to everyone on the target's\n" +
            "square before you move the target – your target will take 3 damage total.\n", Ammo.RED_AMMO, Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "rocketlauncher.json";
        }
    },
    POWER_GLOVE("Power Glove", "basic mode: Choose 1 target on any square\n" +
            "exactly 1 move away. MoveAction onto that square\n" +
            "and give the target 1 damage and 2 marks.\n" +
            "in rocket fist mode: Choose a square\n" +
            "exactly 1 move away. MoveAction onto that square.\n" +
            "You may deal 2 damage to 1 target there.\n" +
            "If you want, you may move 1 more square in\n" +
            "that same direction (but only if it is a legal\n" +
            "move). You may deal 2 damage to 1 target\n" +
            "there, as well.\n" +
            "Notes: In rocket fist mode, you're flying\n" +
            "2 squares in a straight line, punching\n" +
            "1 person per square.\n", Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "powerglove.json";
        }
    },
    RAILGUN("Railgun", "basic mode: Choose a cardinal direction and 1 target in that direction.\n" +
            "Deal 3 damage to it.\n" +
            "in piercing mode: Choose a cardinal direction and 1 or 2 targets in that\n" +
            "direction. Deal 2 damage to each.\n" +
            "Notes: Basically, you're shooting in a straight line and ignoring walls.\n" +
            "You don't have to pick a target on the other side of a wall – it could even\n" +
            "be someone on your own square – but shooting through walls sure is\n" +
            "fun. There are only 4 cardinal directions. You imagine facing one wall or\n" +
            "door, square-on, and firing in that direction. Anyone on a square in that\n" +
            "direction (including yours) is a valid target. In piercing mode,\n" +
            "the 2 targets can be on the same square or on different squares.\n", Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "railgun.json";
        }
    },
    SHOCKWAVE("Shockwave", "basic mode: Choose up to 3 targets on\n" +
            "different squares, each exactly 1 move away.\n" +
            "Deal 1 damage to each target.\n" +
            "in tsunami mode: Deal 1 damage to all\n" +
            "targets that are exactly 1 move away.\n", Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "shockwave.json";
        }
    },
    CYBERBLADE("Cyberblade", "basic effect: Deal 2 damage to 1 target on your square.\n" +
            "with shadowstep: MoveAction 1 square before or after the basic effect.\n" +
            "with slice and dice: Deal 2 damage to a different target on your square.\n" +
            "The shadowstep may be used before or after this effect.\n" +
            "Notes: Combining all effects allows you to move onto a square and\n" +
            "whack 2 people; or whack somebody, move, and whack somebody else;\n" +
            "or whack 2 people and then move.\n", Ammo.YELLOW_AMMO, Ammo.RED_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "cyberblade.json";
        }
    },
    SLEDGEHAMMER("Sledgehammer", "basic mode: Deal 2 damage to 1 target on\n" +
            "your square.\n" +
            "in pulverize mode: Deal 3 damage to 1 target\n" +
            "on your square, then move that target 0, 1,\n" +
            "or 2 squares in one direction.\n" +
            "Notes: Remember that moves go through\n" +
            "doors, but not walls.\n", Ammo.YELLOW_AMMO) {
        @Override
        public String getPath() {
            return super.getPath() + "sledgehammer.json";
        }
    };

    private static final String WEAPONS_PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "weapons" + File.separatorChar;

    private String name;

    /**
     * description of the weapon
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
    WeaponType(String name, String desc, Ammo... ammo) {
        this.name = name;
        this.description = desc;
        reloadingAmmo = new ArrayList<>();
        reloadingAmmo.addAll(Arrays.asList(ammo));
    }

    /**
     * Getter method
     * @return the weapon's directory
     */
    public String getPath() {
        return WEAPONS_PATH;
    }

    /**
     * Getter method
     * @return the weapon's name
     */
    public String getName() {
        return name;
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


