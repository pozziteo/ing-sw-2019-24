package adrenaline.model.deck;

import java.util.List;

public class BaseEffect extends WeaponEffect {

    protected BaseEffect(WeaponEffectRequirement requirement, List<AtomicWeaponEffect> effects) {
        super(requirement, effects);
    }
}
