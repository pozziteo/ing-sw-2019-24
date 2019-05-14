package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class VisibilityRequirement implements WeaponEffectRequirement {

    private boolean visible;

    protected VisibilityRequirement(boolean visible) {
        this.visible = visible;
    }

    @Override
    public List<Player> findTargets(Player attacker, TargetType targetType){

        List<Player> visiblePlayers = new ArrayList<>();
        List<Player> notVisiblePlayers = new ArrayList<>();
        List<Player> players = WeaponEffectRequirement.super.findTargets(attacker, targetType);

        for (Player player : players) {
            if (attacker.canSee(player))
                visiblePlayers.add(player);
            else
                notVisiblePlayers.add(player);
        }

        if (visible)
            return visiblePlayers;
        else
            return notVisiblePlayers;
    }
}
