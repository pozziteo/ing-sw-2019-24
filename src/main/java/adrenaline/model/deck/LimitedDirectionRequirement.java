package adrenaline.model.deck;

import adrenaline.model.player.Action;
import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class LimitedDirectionRequirement extends DirectionRequirement {

    private int maxDistance;

    protected LimitedDirectionRequirement(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public List<Player> findTargets(Player attacker, TargetType targetType) {
        List<Player> limitedTargets = new ArrayList<>();
        List<Player> directionTargets = super.findTargets(attacker, targetType);
        List<Integer> validPositions = Action.findPaths(attacker, maxDistance);
        for (Player target : directionTargets) {
            if (checkPosition(target, validPositions))
                limitedTargets.add(target);
        }
        return limitedTargets;
    }

    private boolean checkPosition(Player target, List<Integer> validSquares) {
        for (int squareId : validSquares) {
            if (target.getPosition().getSquareId() == squareId)
                return true;
        }
        return false;
    }
}
