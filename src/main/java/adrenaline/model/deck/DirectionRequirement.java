package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class DirectionRequirement implements WeaponEffectRequirement {

    protected DirectionRequirement() {

    }

    @Override
    public List<Player> findTargets(Player attacker) {
        List<Player> targets = new ArrayList<>();
        List<Player> players = WeaponEffectRequirement.super.findTargets(attacker);

        int attackerPosition = attacker.getPosition().getSquareId();
        for (Player player : players) {
            int playerPosition = player.getPosition().getSquareId();
            if (playerPosition / 4 == attackerPosition / 4 || playerPosition % 4 == attackerPosition % 4 )
                targets.add(player);
        }

        return targets;
    }
}
