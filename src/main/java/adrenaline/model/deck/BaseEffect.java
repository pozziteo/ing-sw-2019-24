package adrenaline.model.deck;

import java.util.List;

public class BaseEffect extends WeaponEffect {

    protected BaseEffect(WeaponEffectRequirement requirement, TargetType targets, List<AtomicWeaponEffect> effects) {
        super(requirement, targets, effects);
    }
}
