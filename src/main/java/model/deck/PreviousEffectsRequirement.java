package model.deck;

import model.player.Player;

import java.util.List;

public class PreviousEffectsRequirement implements WeaponEffectRequirement {

    private WeaponEffect previousEffect;

    protected PreviousEffectsRequirement(WeaponEffect effect) {
        this.previousEffect = effect;
    }

    @Override
    public List<Player> findTargets(Player attacker) {
        //TODO
        return null;
    }
}
