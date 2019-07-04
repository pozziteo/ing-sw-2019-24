package adrenaline.model.deck;

import java.util.List;

/**
 * Extension of ChainEffect class, this class if for the chain effect
 */
public class ChainEffect extends OptionalEffect {

    private String chainedEffect;

    protected ChainEffect(WeaponEffectRequirement requirement, List<TargetType> targets, List<AtomicWeaponEffect> effects,
                             List<Ammo> additionalCost, boolean usableBeforeBase, boolean alternativeMode, String chainedEffect) {
        super(requirement, targets, effects, additionalCost, usableBeforeBase, alternativeMode);
        this.chainedEffect = chainedEffect;
    }
}
