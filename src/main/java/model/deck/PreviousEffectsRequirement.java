package model.deck;

import model.player.Player;

public class PreviousEffectsRequirement implements WeaponEffectRequirement {

    private WeaponEffect previousEffect;

    protected PreviousEffectsRequirement(WeaponEffect effect) {
        this.previousEffect = effect;
    }

    @Override
    public void findTargets(Player attacker) {
        //TODO
    }
}
