package adrenaline.model.deck;

import java.util.List;

public class ChainEffect extends OptionalEffect {

    private String chainedEffect;

    protected ChainEffect(WeaponEffectRequirement requirement, List<TargetType> targets, List<AtomicWeaponEffect> effects,
                             List<Ammo> additionalCost, boolean usableBeforeBase, boolean alternativeMode, String chainedEffect) {
        super(requirement, targets, effects, additionalCost, usableBeforeBase, alternativeMode);
        this.chainedEffect = chainedEffect;
    }
}
