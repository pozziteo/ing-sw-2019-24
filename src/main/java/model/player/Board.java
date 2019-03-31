package model.player;

import model.player.Player;
import java.util.*;

/**
 * Board is the class where every player has their info
 */

public class Board {
    private String[] damageTaken;
    private String[] receivedMarks;
    private ArrayList<Weapon> unloadedWeapons;
    private ArrayList<Ammo> ownedAmmo;
    //private Action actionCard; ?
    private int[] pointsForKill;

    public void gotHit(int damageAmount, Player inflictedByPlayer) { //add condition to check if damageTaken array is full
        for (int i = 0; i < damageAmount; i++) {
            this.damageTaken.push(inflictedByPlayer.getPlayerColor ());
        }
    }

    public void gotMarked(int amount, Player markedByPlayer) {
        for (int i = 0; i < amount; i++)
            this.receivedMarks.push(markedByPlayer.getPlayerColor ());
    }


    public void addAmmo(Tile t) {
        this.ownedAmmo.add(t);
    }

}