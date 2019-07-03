package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * DirectionRequirement is the implementation of WeaponEffectRequirement class
 */
public class DirectionRequirement implements WeaponEffectRequirement {

    protected DirectionRequirement() {

    }

    /**
     * Method that creates the direction requirement for the victim's move (inflicted by the attacker)
     * @param attacker is the name of the attacker
     * @return the list of available targets
     */
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
