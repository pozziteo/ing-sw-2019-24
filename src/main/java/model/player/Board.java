package model.player;

import model.deck.*;
import java.util.*;

/**
 * Board is the class where every player has their info
 */

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
    //private Action actionCard; ?
    private int[] pointsForKill;

    public Board() {
        this.damageTaken = new ArrayList<>();
        this.receivedMarks = new ArrayList<>();
        this.unloadedWeapons = new ArrayList<Weapon>();
        this.ownedAmmo = new ArrayList<>();
        this.pointsForKill = new int[5];
    }

    public void gotHit(int damageAmount, Player inflictedByPlayer) { //add condition to check if damageTaken array is full
        for (int i = 0; i < damageAmount; i++) {
            this.damageTaken.add(inflictedByPlayer.getPlayerColor ());
        }
    }

    public void gotMarked(int amount, Player markedByPlayer) {
        for (int i = 0; i < amount; i++)
            this.receivedMarks.add(markedByPlayer.getPlayerColor ());
    }

    public ArrayList<String> getDamage() {
        return this.damageTaken;
    }

    public ArrayList<String> getReceivedMarks() {
        return this.receivedMarks;
    }

    public int getAmountGivenByPlayer(Player player) {
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