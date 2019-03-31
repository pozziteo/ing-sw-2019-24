package main.java.model.map;

import main.java.model.deck.Weapon;

public class SpawnPoint extends Square {
    private Weapon[] weapons;

    SpawnPoint() {
        this.weapons = new Weapon[3];
    }
}
