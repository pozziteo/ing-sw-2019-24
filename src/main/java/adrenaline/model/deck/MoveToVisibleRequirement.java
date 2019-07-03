package adrenaline.model.deck;

import adrenaline.model.player.Action;
import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * MoveToVisibleRequirement is the implementation of WeaponEffectRequirement
 */
public class MoveToVisibleRequirement implements WeaponEffectRequirement {

    private int movements;

    protected MoveToVisibleRequirement(int movements) {
        this.movements = movements;
    }

    /**
     * Method to find the list of available targets under the 'movements' condition (maximum number of movements)
     * @param attacker is the attacker
     * @return the list of available targets
     */
    @Override
    public List<Player> findTargets(Player attacker) {
        List<Player> targets = new ArrayList<>();
        List<Player> players = WeaponEffectRequirement.super.findTargets(attacker);

        for (Player player : players) {
            List<Integer> playerPaths = Action.findPaths(player, movements);
            for (int squareId : playerPaths) {
                if (attacker.canSee(player.getGame().getMap().getSquare(squareId))) {
                    targets.add(player);
                    break;
                }
            }
        }
        return targets;
    }
}
