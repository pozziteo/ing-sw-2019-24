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
        boolean legal = true;
        if (targets.size() == 1) {
            //apply every atomic effect to target.get(0)

            //check if targets.get(0) are legal for every atomic effect
            for (AtomicWeaponEffect atomicEffect : effects.getLast().getEffects())
                if (!isAtomicTargetLegal(targets.get(0), 0)) {
                    legal = false;
                    break;
                }

            //apply atomic effects
            if (legal) {
                for (AtomicWeaponEffect atomicEffect : effects.getLast().getEffects())
                    applyEffectToAtomicTargets(targets.get(0));
            } else {
                throw new UnreachableTargetException();
            }
        } else {
            //apply one atomic effect per target

        }
    }

    private boolean isAtomicTargetLegal(AtomicTarget target, int index) {
        if (target.getTargetNames () == null && target.getSquareId () == -1)
            return effects.getLast ().getTargets ().get(index).isCompliantTargets (attacker, null, -1);
        else if (target.getTargetNames () == null && target.getSquareId () != -1)
            return effects.getLast ().getTargets ().get(index).isCompliantTargets (attacker, null, target.getSquareId());
        else if (target.getTargetNames() != null && target.getSquareId() == -1) {
            List<Player> targets = new ArrayList<>();
            for (String name : target.getTargetNames())
                targets.add(attacker.getGame().findByNickname(name));
            return effects.getLast().getTargets().get(index).isCompliantTargets(attacker, targets, -1);
        } else if (target.getTargetNames() != null && target.getSquareId() != -1) {
            List<Player> targets = new ArrayList<>();
            for (String name : target.getTargetNames())
                targets.add(attacker.getGame().findByNickname(name));
            return effects.getLast().getTargets().get(index).isCompliantTargets(attacker, targets, target.getSquareId());
        }
        return false;
    }

    private void applyEffectToAtomicTargets(AtomicTarget target) {
        if (target.getTargetNames () == null && target.getSquareId () == -1) {
            for (AtomicWeaponEffect atomicEffect : effects.getLast ().getEffects ())
                atomicEffect.applyEffect (attacker, null, (Integer[])null);
        } else if (target.getTargetNames () == null && target.getSquareId () != -1) {

        }
    }

    public boolean isEndAction() {
        return this.endAction;
    }

    public void setEndAction(boolean value) {
        this.endAction = value;
    }
}
