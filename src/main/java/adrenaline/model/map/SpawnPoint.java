package adrenaline.model.map;

import adrenaline.model.deck.Weapon;

import java.util.*;

/**
 * Spawn Point is a special Square in which a player can spawn after being killed
 * and from where can grab weapons
 */
public class SpawnPoint extends Square {

    /**
     * weapons is a static array which contains the currently available
     * weapons on the spawn point
     */
    private Weapon[] weapons;

    /**
     * Constructor which initialize the attributes of a spawn point
     * @param id is the square id of the spawn point
     * @param color is the room's color where the spawn point is located
     * @param links is a list which contains the squares' id reachable from
     *              the spawn point
     */
    public SpawnPoint(int id, String color, List<Integer> links) {
        super(id, color, true, links);
        this.weapons = new Weapon[3];
    }

    /**
     * Getter to obtain the weapons currently placed on the spawn point
     * @return the weapons on the spawn point
     */
    public Weapon[] getWeapons() {
        return this.weapons;
    }

    /**
     * Setter to place weapons on the spawn point
     */
    public void setWeapons(Weapon w) {
        int i;
        for (i = 0; this.getWeapons()[i] != null; i++);
        this.weapons[i] = w;
    }

    /**
     * Method to substitute a weapon on the spawn point when a player who already has
     * three weapons tries to grab one
     * @param w is a Weapon card which substitutes the old one
     * @param i is the index of the weapon grabbed by the player
     */
    public void changeWeapon(Weapon w, int i) {
        weapons[i] = w;
    }

    /**
     * Method to remove a weapon from a spawn point
     * @param w is the weapon to be removed
     */
    public void removeWeapon(Weapon w) {
        for (int i = 0; i < 3; i++) {
            String name = weapons[i].getWeaponsName ();
            if (w.getWeaponsName ().equals(name)) {
                changeWeapon(null, i);
            }
        }
    }
}
