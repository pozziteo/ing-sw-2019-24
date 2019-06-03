package adrenaline.model.player;

import adrenaline.model.deck.Weapon;

import java.util.List;

public class Shoot implements Action {
    private boolean baseUsed;

    public Shoot() {
        this.baseUsed = false;

    }

    public void performAttack(Player attacker, List<Player> targets, Weapon weapon) {

    }

}
