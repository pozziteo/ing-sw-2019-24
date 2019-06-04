package adrenaline.model.player;

import adrenaline.exceptions.UnreachableTargetException;
import adrenaline.model.deck.AtomicWeaponEffect;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.WeaponEffect;

import java.util.LinkedList;
import java.util.List;

public class Shoot implements Action {
    private Player attacker;
    private boolean baseUsed;
    private Weapon chosenWeapon;
    private LinkedList<WeaponEffect> effects;
    private boolean endAction;

    public Shoot(Player attacker) {
        this.attacker = attacker;
        this.baseUsed = false;
        this.endAction = false;
        this.effects = new LinkedList<> ();
    }

    public void setBaseUsed(boolean value) {
        this.baseUsed = value;
    }

    public boolean isBaseUsed() {
        return this.baseUsed;
    }

    public Weapon getChosenWeapon() {
        return this.chosenWeapon;
    }

    public void setChosenWeapon(Weapon weapon) {
        this.chosenWeapon = weapon;
    }

    public void addEffectToApply(WeaponEffect effect) {
        this.effects.add(effect);
    }

    public WeaponEffect getEffectToApply() {
        return this.effects.getLast ();
    }

    public void setEffectTargetAreas(List<Integer> id) {
        int i = 0;
        for (AtomicWeaponEffect atomicEffect : effects.getLast ().getEffects ()) {
            atomicEffect.applyEffect (attacker, null, id.get (i));
            i++;
        }
    }

    public void setEffectTargets(List<Player> targets) throws UnreachableTargetException {
        for (AtomicWeaponEffect atomicEffect : effects.getLast ().getEffects ()) {
            for (Player target : targets) {
                if (checkTargetRequirements()) {
                    atomicEffect.applyEffect (attacker, target, (Integer[]) null);
                } else {
                    throw new UnreachableTargetException();
                }
            }
        }
    }

    public boolean checkTargetRequirements() {
        return true;
    }

    public boolean isEndAction() {
        return this.endAction;
    }

    public void setEndAction(boolean value) {
        this.endAction = value;
    }

    public void performAttack(Player attacker, List<Player> targets, Weapon weapon) {

    }

}
