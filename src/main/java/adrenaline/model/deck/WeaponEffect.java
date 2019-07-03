package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.*;

/**
 * Abstract class for the effects of a weapon, implemented by subclasses
 */
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

    /**
     * Method to use the effect of a weapon
     * @param attacker is the players attacking
     * @param target is the victim
     * @param id is the id of the square
     */
    public void useEffect(Player attacker, Player target, Integer... id) {
        for (AtomicWeaponEffect effect : effects) {
            effect.applyEffect(attacker, target, id);
        }
    }

    /**
     * Getter method
     * @return the weapon's requiremnt
     */
    public WeaponEffectRequirement getRequirement() {
        return this.requirement;
    }

    /**
     * Getter method
     * @return the list of target types
     */
    public List<TargetType> getTargetTypes() {
        return this.targetTypes;
    }

    /**
     * Getter method
     * @return the list of a weapon's effects
     */
    public List<AtomicWeaponEffect> getEffects() {
        return this.effects;
    }

    /**
     * Setter method to set a list of player as targets
     * @param targets is the list of player to be set
     */
    public void setTargets(List<Player> targets) {
        this.targets = targets;
    }

    /**
     * Getter method
     * @return the list of targets
     */
    public List<Player> getTargets() {
        return this.targets;
    }

}
