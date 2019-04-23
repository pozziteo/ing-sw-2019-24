package model.deck;

import model.player.Player;

public class VisibilityRequirement implements WeaponEffectRequirement {

    private boolean visible;

    protected VisibilityRequirement(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void findTargets(Player attacker){
        //TODO
    }
}
