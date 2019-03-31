package model.map;

import model.deck.*;

public class SpawnPoint extends Square {
    private Weapon[] weapons;

    public SpawnPoint(int x, int y, String color) {
        super(x, y, color, true);
        this.weapons = new Weapon[3];
    }
}
