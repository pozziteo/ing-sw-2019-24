package model.deck;

import model.player.Player;

import java.util.List;

public interface WeaponEffectRequirement {
    List<Player> findTargets(Player attacker);
}
