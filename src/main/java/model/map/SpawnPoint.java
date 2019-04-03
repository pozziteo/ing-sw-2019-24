package model.map;

import model.deck.*;

public class SpawnPoint extends Square {
    private Weapon[] weapons;

    public SpawnPoint(int x, int y, String color) {
        super(x, y, color, true);
        this.weapons = new Weapon[3];
    }

    public Weapon[] getWeapons() {
        return this.weapons;
    }

    public void setWeapons(Weapon w) {
        int i;
        for (i = 0; this.getWeapons()[i] != null; i++);
        this.weapons[i] = w;
    }

    public void changeWeapon(Weapon w, int i) {
        this.weapons[i] = w;
    }

    public void removeWeapon(Weapon w) {
        for (int i = 0; i < 3; i++) {
            String name = this.getWeapons()[i].getWeaponsName ();
            if (w.getWeaponsName ().equals(name)) {
                changeWeapon(null, i);
            }
        }
    }
}
