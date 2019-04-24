package model.deck;

import model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class DistanceRequirement implements WeaponEffectRequirement {

    private int minDistance;
    private int maxDistance;

    protected DistanceRequirement(int minDistance, int maxDistance) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    @Override
    public List<Player> findTargets(Player attacker) {
        List<Player> targets = new ArrayList<>();
        List<Player> players = new ArrayList<>(attacker.getGame().getPlayers());
        players.remove(attacker);

        

        return null;
    }
}
