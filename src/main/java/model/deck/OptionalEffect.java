package model.deck;

import model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class OptionalEffect extends WeaponEffect {

    private List<Ammo> additionalCost;
    private boolean alternativeMode;

    protected OptionalEffect(List<AtomicWeaponEffect> effects, List<Ammo> additionalCost, boolean alternativeMode) {
        super(effects);
        this.additionalCost = new ArrayList<>();

        if (!additionalCost.isEmpty())
            this.additionalCost.addAll(additionalCost);

        this.alternativeMode = alternativeMode;
    }

    public List<Ammo> getAdditionalCost() {
        return this.additionalCost;
    }

    public boolean isAlternativeMode() {
        return this.alternativeMode;
    }

    @Override
    public void useEffect(Player attacker, Player target, Integer... id) {
        if (!additionalCost.isEmpty())
            payAdditionalCost(attacker);
        super.useEffect(attacker, target, id);
    }

    public void payAdditionalCost(Player attacker) {
        List<Ammo> actualAmmo = attacker.getBoard().getOwnedAmmo();
        for (Ammo ammo : this.additionalCost) {
            actualAmmo.remove(ammo);
        }
    }
}
