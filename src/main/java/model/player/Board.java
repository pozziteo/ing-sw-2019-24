package model.player;

import java.io.*;
import java.util.*;

/**
 * Board is the class where every player has their
 */

public class Board {
    private String[12] damageTaken;
    private String[3] receivedMarks;
    private ArrayList<Weapon> unloadedWeapons;
    private ArrayList<Ammo> ownedAmmo;
    //private Action actionCard; ?
    private int[5] pointsForKill;

    public void gotHit(int damageAmount, Player inflictedByPlayer) { //add condition to check if damageTaken array is full
        for (int i = 0; i < damageAmount; i++) {
            this.damageTaken.push(inflictedByPlayer.playerID);
        }
    }

    public void gotMarked(int amount, String markedByPlayer) {
        try {
            for (int i = 0; i < amount; i++)
                this.receivedMarks.push(markedByPlayer);
        } catch (Exception exception) {
            System.out.println("Max amount of marks exceeded");
        }
    }


    public void addAmmo(Tile t) {
        this.ownedAmmo.add(t);
    }

}