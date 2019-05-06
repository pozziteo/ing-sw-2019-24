package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MoveToVisibleRequirement implements WeaponEffectRequirement {

    private int movements;

    protected MoveToVisibleRequirement(int movements) {
        this.movements = movements;
    }

    @Override
    public List<Player> findTargets(Player attacker) {
        List<Player> targets = new ArrayList<>();
        List<Player> players = WeaponEffectRequirement.super.findTargets(attacker);


        return targets;
    }
}
