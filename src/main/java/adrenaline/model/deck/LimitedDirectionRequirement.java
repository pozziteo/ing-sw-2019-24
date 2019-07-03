package adrenaline.model.deck;

import adrenaline.model.player.Action;
import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension of DirectionRequirement
 */
public class LimitedDirectionRequirement extends DirectionRequirement {

    private int maxDistance;

    protected LimitedDirectionRequirement(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    /**
     * Method to find the available targets under the condition of maxDistance (number of maximum movements)
     * @param attacker is the name of the attacker
     * @return the list of available targets
     */
    @Override
    public List<Player> findTargets(Player attacker) {
        List<Player> limitedTargets = new ArrayList<>();
        List<Player> directionTargets = super.findTargets(attacker);
        List<Integer> validPositions = Action.findPaths(attacker, maxDistance);
        for (Player target : directionTargets) {
            if (checkPosition(target, validPositions))
                limitedTargets.add(target);
        }
        return limitedTargets;
    }

    /**
     * Method to establish if a target is in a valid square
     * @param target is the target
     * @param validSquares is the list  of valid squares
     * @return true (if the target is in those), else false
     */
    private boolean checkPosition(Player target, List<Integer> validSquares) {
        for (int squareId : validSquares) {
            if (target.getPosition().getSquareId() == squareId)
                return true;
        }
        return false;
    }
}
