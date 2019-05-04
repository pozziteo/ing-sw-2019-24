package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.*;

public abstract class WeaponEffect {

    private List<AtomicWeaponEffect> effects;

    protected WeaponEffect(List<AtomicWeaponEffect> effects) {
        this.effects = new ArrayList<>(effects);
    }

    public void useEffect(Player attacker, Player target, Integer... id) {
        for (AtomicWeaponEffect effect : effects) {
            effect.applyEffect(attacker, target, id);
        }
    }

}
