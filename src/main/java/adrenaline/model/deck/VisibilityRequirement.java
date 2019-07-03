package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * VisibilityRequirement is a implementation of WeaponEffectRequirement
 * It creates visibility requirements for a weapon
 */
public class VisibilityRequirement implements WeaponEffectRequirement {

    private boolean visible;

    protected VisibilityRequirement(boolean visible) {
        this.visible = visible;
    }

    /**
     * Method to find the available target under the condition of 'visible' (boolean that tells
     * if the attacker can see a target)
     * @param attacker is the player attacking
     * @return the list of visible targets
     */
    @Override
    public List<Player> findTargets(Player attacker){

        List<Player> visiblePlayers = new ArrayList<>();
        List<Player> notVisiblePlayers = new ArrayList<>();
        List<Player> players = WeaponEffectRequirement.super.findTargets(attacker);

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
