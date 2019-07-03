package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension of WeaponEffect, it creates the optional effects of a weapon
 */
public class OptionalEffect extends WeaponEffect {

    private List<Ammo> additionalCost;
    private boolean usableBeforeBase;
    private boolean alternativeMode;

    /**
     * Method to set the optional effects available
     * @param requirement is the weapon requirement
     * @param targets is the list of targets
     * @param effects is the list of available effects
     * @param additionalCost is the list of ammo required for the additional effects
     * @param usableBeforeBase is true if you can use the additional effect before the base effect, false otherwise
     * @param alternativeMode is true if you can use the additional effect instead of the base effect
     */
    protected OptionalEffect(WeaponEffectRequirement requirement, List<TargetType> targets, List<AtomicWeaponEffect> effects,
                             List<Ammo> additionalCost, boolean usableBeforeBase, boolean alternativeMode) {
        super(requirement, targets, effects);
        this.additionalCost = new ArrayList<>();

        if (!additionalCost.isEmpty())
            this.additionalCost.addAll(additionalCost);

        this.usableBeforeBase = usableBeforeBase;
        this.alternativeMode = alternativeMode;
    }

    /**
     * Getter method
     * @return the list of additional ammo required
     */
    public List<Ammo> getAdditionalCost() {
        return this.additionalCost;
    }

    /**
     * Method to establish if the effect is alternative
     * @return true if the effect is alternative, false otherwise
     */
    public boolean isAlternativeMode() {
        return this.alternativeMode;
    }

    /**
     * Method to establish if the effect is usable before the base effect
     * @return true if the effect is usable before the base effect, false otherwise
     */
    public boolean isUsableBeforeBase() {
        return this.usableBeforeBase;
    }

    /**
     * Method to establish if an effect is usable: it checks if the player has enough ammo
     * @param attacker is the player
     * @return true if he has enough ammo, false otherwise
     */
    public boolean isUsable(Player attacker) {
        return attacker.hasEnoughAmmo (additionalCost);
    }

    /**
     * Method to use an effect
     * @param attacker is the player who is attacking
     * @param target is the victim
     * @param id is the position
     */
    @Override
    public void useEffect(Player attacker, Player target, Integer... id) {
        if (!additionalCost.isEmpty())
            payAdditionalCost(attacker);
        super.useEffect(attacker, target, id);
    }

    /**
     * Method to pay an additional cost
     * @param attacker is the name of the attacker
     */
    private void payAdditionalCost(Player attacker) {
        attacker.payAmmo (additionalCost);
    }
}
