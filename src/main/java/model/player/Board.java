package model.player;

import model.deck.*;
import java.util.*;

/**
 * Board is the class where every player has their info
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

}