package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.*;

public abstract class WeaponEffect {

    private WeaponEffectRequirement requirement;
    private List<TargetType> targetTypes;
    private List<AtomicWeaponEffect> effects;
    private List<Player> targets;

    protected WeaponEffect(WeaponEffectRequirement requirement, List<TargetType> targets, List<AtomicWeaponEffect> effects) {
        this.requirement = requirement;
        this.targetTypes = targets;
        this.effects = new ArrayList<>(effects);
        this.targets = new ArrayList<> ();
    }

    public void useEffect(Player attacker, Player target, Integer... id) {
        for (AtomicWeaponEffect effect : effects) {
            effect.applyEffect(attacker, target, id);
        }
    }

    public WeaponEffectRequirement getRequirement() {
        return this.requirement;
    }

    public List<TargetType> getTargetTypes() {
        return this.targetTypes;
    }

    public List<AtomicWeaponEffect> getEffects() {
        return this.effects;
    }

    public void setTargets(List<Player> targets) {
        this.targets = targets;
    }

    public List<Player> getTargets() {
        return this.targets;
    }

}
