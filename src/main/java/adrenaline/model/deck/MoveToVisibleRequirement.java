package adrenaline.model.deck;

import adrenaline.model.player.Action;
import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MoveToVisibleRequirement implements WeaponEffectRequirement {

    private int movements;

    protected MoveToVisibleRequirement(int movements) {
        this.movements = movements;
    }

    @Override
    public List<Player> findTargets(Player attacker, TargetType targetType) {
        List<Player> targets = new ArrayList<>();
        List<Player> players = WeaponEffectRequirement.super.findTargets(attacker, targetType);

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
