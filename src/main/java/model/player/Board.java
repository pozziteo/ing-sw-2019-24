package model.player;

import java.util.ArrayList;

public class Board {
    private String damageTaken;
    private ArrayList<Weapon> unloadedWeapons;
    private ArrayList<Ammo> ownedAmmo;

    public void addAmmo(Tile t) {
        this.ownedAmmo.add(t);
    }

}