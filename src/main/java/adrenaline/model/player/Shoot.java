package adrenaline.model.player;

import adrenaline.data.data_for_server.data_for_game.AtomicTarget;
import adrenaline.exceptions.UnreachableTargetException;
import adrenaline.model.deck.AtomicWeaponEffect;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.WeaponEffect;

import java.util.ArrayList;
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

    public void setEffectTargets(List<AtomicTarget> targets) throws UnreachableTargetException {
        if (targets.size() == 1) {
            //apply every atomic effect to target.get(0)
            setAtomicTargets (0, targets.get(0));
        } else {
            //apply one atomic effect per target
            int i = 0;
            for (AtomicTarget target : targets) {
                setAtomicTargets (i, target);
            }
        }
    }

    private void setAtomicTargets(int n, AtomicTarget target) throws UnreachableTargetException {
        if (target.getTargetNames () == null && target.getSquareId () == -1) {
            if (checkTargetRequirements (0))
                for (AtomicWeaponEffect atomicEffect : effects.getLast ().getEffects ())
                    atomicEffect.applyEffect (attacker, null, (Integer[])null);
            else
                throw new UnreachableTargetException ();
        } else if (target.getTargetNames () == null && target.getSquareId () != -1) {

        }
    }

    private boolean checkTargetRequirements(int n) {
        return effects.getLast ().getTargets ().get(n).isCompliantTargets (attacker, null);
    }

    public boolean isEndAction() {
        return this.endAction;
    }

    public void setEndAction(boolean value) {
        this.endAction = value;
    }
}
