package adrenaline.model.player;

import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.powerup.PowerUp;

import java.util.*;

/**
 * Board is an object which contains information about the current state
 * of a player in the game, like his health bar, his marks received by other players
 * and his weapon currently unloaded
 */

public class Board {
    private ArrayList<String> damageTaken;
    private ArrayList<String> receivedMarks;
    private ArrayList<Weapon> unloadedWeapons;
    private ArrayList<Ammo> ownedAmmo;
    private int[] pointsForKill;
    private Player player;
    private int points;

    public Board(Player player) {
        this.player = player;
        this.damageTaken = new ArrayList<>();
        this.receivedMarks = new ArrayList<>();
        this.unloadedWeapons = new ArrayList<>();
        this.ownedAmmo = new ArrayList<>();
        this.ownedAmmo.add(Ammo.RED_AMMO);
        this.ownedAmmo.add(Ammo.BLUE_AMMO);
        this.ownedAmmo.add(Ammo.YELLOW_AMMO);
        this.pointsForKill = new int[5];
        this.points = 0;
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

    public int[] getPointsForKill() {
        return this.pointsForKill;
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
     * @param attacker
     */

    public void gotHit(int amount, Player attacker) {
        if(!this.player.isWaitingForRespawn()){
            for (int i = 0; i < amount; i++) {
                setDamageTaken(attacker);
            }
            if (player.canSee (attacker) && player.hasTagbackGrenade ()) {
                player.setCanTagbackGrenade (true);
            }
            if (this.getDamageTaken().size() > 10) {
                this.player.hasDied ();
                this.getDamageTaken().clear();
                this.player.setWaitingForRespawn(true);
                if (player.getOwnedPowerUps().size() < 4) {
                    PowerUp powerUp = (PowerUp) player.getGame().getPowerUpsDeck().drawCard();
                    player.getOwnedPowerUps().add(powerUp);
                }
            }
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

    /**
     * Method that tells who is the first blood
     * @param player is the player who might have dealt damage first
     * @return true if that's the case
     */
    public boolean isFirstBlood(Player player){
        return this.getDamageTaken().get(0).equalsIgnoreCase(player.getPlayerColor());
    }

    /**
     * Getter method to obtain a player's points
     * @return amount of points
     */

    public int getPointTokens() {
        return this.points;
    }

    /**Setter method to set a player's points
     * @param amount of points to be given
     */

    private void setPointTokens(int amount) {
        this.points = amount;
    }


    /**
     * Method to add points to a player after a kill
     * @param amount
     */

    public void addPointTokens(int amount) {
        int points = this.getPointTokens ();
        points += amount;
        setPointTokens (points);
    }

}