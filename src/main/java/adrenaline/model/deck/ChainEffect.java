package adrenaline.model.deck;

import java.util.List;

public class ChainEffect extends OptionalEffect {

    private WeaponEffect chainedEffect;

    protected ChainEffect(WeaponEffectRequirement requirement, List<AtomicWeaponEffect> effects,
                             List<Ammo> additionalCost, boolean alternativeMode, WeaponEffect chainedEffect) {
        super(requirement, effects, additionalCost, alternativeMode);
        this.chainedEffect = chainedEffect;
    }
}
