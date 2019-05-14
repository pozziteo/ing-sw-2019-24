package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public interface WeaponEffectRequirement {
    default List<Player> findTargets(Player attacker, TargetType targetType) {

        List<Player> players = targetType.findTargets ();
        players.remove(attacker);

        return players;
    }
}
