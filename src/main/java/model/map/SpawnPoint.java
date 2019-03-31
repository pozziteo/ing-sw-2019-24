package model.map;

import model.deck.*;

public class SpawnPoint extends Square {
    private Weapon[] weapons;

    SpawnPoint() {
        this.weapons = new Weapon[3];
    }
}
