package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * WeaponEffectRequirement is implemented by subclasses
 */
public interface WeaponEffectRequirement {
    default List<Player> findTargets(Player attacker) {

        List<Player> players = new ArrayList<>(attacker.getGame().getPlayers());
        players.remove(attacker);

        return players;
    }
}
