package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.List;

public class NullRequirement implements WeaponEffectRequirement {

    @Override
    public List<Player> findTargets(Player attacker) {
        return WeaponEffectRequirement.super.findTargets (attacker);
    }
}
