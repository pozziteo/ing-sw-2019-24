package adrenaline.model.deck;

import java.util.List;

/**
 * Extension of WeaponEffect class, this class is for the base effect of a weapon
 */
public class BaseEffect extends WeaponEffect {

    protected BaseEffect(WeaponEffectRequirement requirement, List<TargetType> targets, List<AtomicWeaponEffect> effects) {
        super(requirement, targets, effects);
    }
}
