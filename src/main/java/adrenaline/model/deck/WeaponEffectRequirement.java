package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public interface WeaponEffectRequirement {
    default List<Player> findTargets(Player attacker) {

        List<Player> players = new ArrayList<>(attacker.getGame().getPlayers());
        players.remove(attacker);

        return players;
    }
}