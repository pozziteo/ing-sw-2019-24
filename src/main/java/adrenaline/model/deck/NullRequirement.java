package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.List;

/**
 * NullRequirement is the implementation of WeaponEffectRequirement
 */
public class NullRequirement implements WeaponEffectRequirement {

    /**
     * Method that finds the targets
     * @param attacker is the player who is attacking
     * @return a list of available targets
     */
    @Override
    public List<Player> findTargets(Player attacker) {
        return WeaponEffectRequirement.super.findTargets (attacker);
    }
}
