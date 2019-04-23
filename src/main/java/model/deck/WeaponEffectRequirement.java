package model.deck;

import model.player.Player;

public interface WeaponEffectRequirement {
    void findTargets(Player attacker);
}
