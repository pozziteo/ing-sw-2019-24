package adrenaline.model.player;

import adrenaline.model.deck.Weapon;

import java.util.List;

public class Shoot implements Action {
    private List<Weapon> weapons;

    public Shoot(Player shooter) {
        this.weapons = shooter.getOwnedWeapons ();
    }

    public void performAttack(Player attacker, List<Player> targets, Weapon weapon) {

    }

}
