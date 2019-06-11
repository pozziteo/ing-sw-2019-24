package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class OptionalEffect extends WeaponEffect {

    private List<Ammo> additionalCost;
    private boolean usableBeforeBase;
    private boolean alternativeMode;

    protected OptionalEffect(WeaponEffectRequirement requirement, List<TargetType> targets, List<AtomicWeaponEffect> effects,
                             List<Ammo> additionalCost, boolean usableBeforeBase, boolean alternativeMode) {
        super(requirement, targets, effects);
        this.additionalCost = new ArrayList<>();

        if (!additionalCost.isEmpty())
            this.additionalCost.addAll(additionalCost);

        this.usableBeforeBase = usableBeforeBase;
        this.alternativeMode = alternativeMode;
    }

    public List<Ammo> getAdditionalCost() {
        return this.additionalCost;
    }

    public boolean isAlternativeMode() {
        return this.alternativeMode;
    }

    public boolean isUsableBeforeBase() {
        return this.usableBeforeBase;
    }

    public boolean isUsable(Player attacker) {
        return attacker.hasEnoughAmmo (additionalCost);
    }

    @Override
    public void useEffect(Player attacker, Player target, Integer... id) {
        if (!additionalCost.isEmpty())
            payAdditionalCost(attacker);
        super.useEffect(attacker, target, id);
    }

    private void payAdditionalCost(Player attacker) {
        attacker.payAmmo (additionalCost);
    }
}
