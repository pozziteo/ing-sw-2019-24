package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.*;

public abstract class WeaponEffect {

    private WeaponEffectRequirement requirement;
    private List<AtomicWeaponEffect> effects;

    protected WeaponEffect(WeaponEffectRequirement requirement, List<AtomicWeaponEffect> effects) {
        this.requirement = requirement;
        this.effects = new ArrayList<>(effects);
    }

    public void useEffect(Player attacker, Player target, Integer... id) {
        for (AtomicWeaponEffect effect : effects) {
            effect.applyEffect(attacker, target, id);
        }
    }

    public WeaponEffectRequirement getRequirement() {
        return this.requirement;
    }

    public List<AtomicWeaponEffect> getEffects() {
        return this.effects;
    }

}
