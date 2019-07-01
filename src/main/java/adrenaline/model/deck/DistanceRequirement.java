package adrenaline.model.deck;

import adrenaline.model.player.Action;
import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class DistanceRequirement implements WeaponEffectRequirement {

    private int minDistance;
    private int maxDistance;

    protected DistanceRequirement(int minDistance, int maxDistance) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }


    /**
     * Method to find the possible targets that the attacker may hit with a certain effect.
     * In this implementation they are calculated by finding the squares that match the constraints
     * of distance and evaluating the players on those squares,
     * also applying the visibility bond - which is always implied
     *
     * @param attacker is the player who wants to attack
     * @return a list of players that may be hit with an effect under this requirement
     */
    @Override
    public List<Player> findTargets(Player attacker) {
        List<Player> targets = new ArrayList<>();
        List<Player> players = WeaponEffectRequirement.super.findTargets(attacker);

        List<Integer> validSquares = findValidSquares(attacker);

        for (Player player : players)
            if (checkPosition(player, validSquares) && attacker.canSee(player))
                targets.add(player);

        return targets;
    }


    /**
     * Method to obtain the squares that satisfy the distance requirement from the attacker. Three different cases can
     * occur:
     * 1 - minDistance is 0 -> a valid value of maxDistance is supposed (unless this would be a visibility requirement
     *      and not a distance one), the squares are the paths from the attacker for maxDistance movements
     *
     * 2 - minDistance is not 0 and maxDistance is 0 -> there is only a minimum distance from the attacker, the valid
     *      squares are all the squares except the ones under the minimum distance
     *
     * 3 - none of the previous -> there are both a minimum and a maximum distance, the valid squares are the paths from
     *      the attacker for maxDistance movements except the ones under the minimum distance
     *
     * @param attacker is the player who the squares are calculated from
     * @return all the valid squares that satisfy the constraints
     */
    private List<Integer> findValidSquares(Player attacker) {

        List<Integer> validSquares = new ArrayList<>();

        if (minDistance == 0) {
            validSquares = Action.findPaths(attacker, maxDistance);
        }
        else if (maxDistance == 0) {
            for (int id=0; id < attacker.getGame().getMap().getDimension(); id++)
                validSquares.add(id);
            List<Integer> tooNear = Action.findPaths(attacker, minDistance-1);
            validSquares.removeAll(tooNear);
        }
        else {
            validSquares = Action.findPaths(attacker, maxDistance);
            List<Integer> tooNear = Action.findPaths(attacker, minDistance-1);
            validSquares.removeAll(tooNear);
        }

        return validSquares;
    }

    /**
     * Method to establish if a target is in a valid square
     * @param target is the name of the target
     * @param validSquares is the list of valid squares
     * @return true (if the target is in those), else false
     */
    private boolean checkPosition(Player target, List<Integer> validSquares) {

        for (Integer squareId : validSquares)
            if (target.getPosition().getSquareId() == squareId)
                return true;
        return false;

    }
}

