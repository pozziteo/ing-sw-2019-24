package adrenaline.model.player;

import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.Weapon;

import java.io.Serializable;
import java.util.*;

/**
 * Board is an object which contains information about the current state
 * of a player in the game, like his health bar, his marks received by other players
 * and his weapon currently unloaded
 */

public class Board implements Serializable {
    private ArrayList<String> damageTaken;
    private ArrayList<String> receivedMarks;
    private ArrayList<Weapon> unloadedWeapons;
    private ArrayList<Ammo> ownedAmmo;
    private int[] pointsForKill;

    public Board() {
        this.damageTaken = new ArrayList<>();
        this.receivedMarks = new ArrayList<>();
        this.unloadedWeapons = new ArrayList<>();
        this.ownedAmmo = new ArrayList<>();
        this.ownedAmmo.add(Ammo.RED_AMMO);
        this.ownedAmmo.add(Ammo.BLUE_AMMO);
        this.ownedAmmo.add(Ammo.YELLOW_AMMO);
        this.pointsForKill = new int[5];
    }

    /**
     * Getter method to obtain the list of players that damaged this player
     * @return damageTaken
     */

    public List<String> getDamageTaken() {
        return this.damageTaken;
    }

    /**
     * Setter method to add a player to the damageTaken list of this player
     * @param p
     */

    public void setDamageTaken(Player p) {
        this.damageTaken.add(p.getPlayerColor ());
    }

    /**
     * Getter method to obtain the list of players who marked this player
     * @return receivedMarks
     */

    public List<String> getReceivedMarks() {
        return this.receivedMarks;
    }

    /**
     * Setter method to add a player who marked this player to the latter's receivedMarks list
     * @param p
     */

    public void setReceivedMarks(Player p) {
        this.receivedMarks.add(p.getPlayerColor ());
    }

    /**
     * Getter method to obtain the list of unloaded weapons owned by this player
     * @return
     */

    public List<Weapon> getUnloadedWeapons() {
        return this.unloadedWeapons;
    }

    /**
     *  Setter method to add a weapon to the unloadedWeapons list after it's been used
     * @param w
     */

    public void setUnloadedWeapons(Weapon w) {
        this.unloadedWeapons.add(w);
    }

    /**
     * Getter method to obtain the list of ammo owned by this player
     * @return ownedAmmo
     */

    public List<Ammo> getOwnedAmmo() {
        return this.ownedAmmo;
    }

    /**
     * Setter method to add ammo to the ownedAmmo list after this player grabbed it
     * @param a
     */

    public void setOwnedAmmo(Ammo a) {
        this.getOwnedAmmo ().add(a);
    }

    public int getAmountOfAmmo(Ammo a) {
        int amount = 0;
        for (int i = 0; i < this.getOwnedAmmo ().size(); i++) {
            if (a.getColor ().equals(this.getOwnedAmmo ().get(i).getColor ())) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * Method to add the right amount of damage depending on the weapon's effect after a player hit this player
     * @param amount
     * @param inflictedByPlayer
     */

    public void gotHit(int amount, Player inflictedByPlayer) {
        for (int i = 0; i < amount; i++) {
            setDamageTaken (inflictedByPlayer);
        }
    }

    /**
     * Method to add the right amount of marks to this player
     * @param amount
     * @param markedByPlayer
     */

    public void gotMarked(int amount, Player markedByPlayer) {
        for (int i = 0; i < amount; i++)
            setReceivedMarks (markedByPlayer);
    }

    /**
     * Method to get the amount of damage given by a specific player
     * @param player
     * @return amount of damage given by player
     */

    public int getDamageAmountGivenByPlayer(Player player) {
        String playerColor = player.getPlayerColor ();
        int amount = 0;

        for (int i = 0; i < this.getDamageTaken ().size(); i++) {
            if (playerColor.equals(this.getDamageTaken ().get(i))) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * Method to obtain the amount of marks this player has received from a specific player
     * @param player
     * @return amount of marks given by player
     */


    public int getMarksAmountGivenByPlayer(Player player) {
        String playerColor = player.getPlayerColor ();
        int amount = 0;

        for (int i = 0; i < this.getReceivedMarks ().size(); i++) {
            if (playerColor.equals(this.getReceivedMarks ().get(i))) {
                amount++;
            }
        }
        return amount;
    }
}