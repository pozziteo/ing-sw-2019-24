package model.deck;

import model.player.Player;

public class DistanceRequirement implements WeaponEffectRequirement {

    private int minDistance;
    private int maxDistance;

    protected DistanceRequirement(int minDistance, int maxDistance) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    @Override
    public void findTargets(Player attacker) {
        //TODO
    }
}
