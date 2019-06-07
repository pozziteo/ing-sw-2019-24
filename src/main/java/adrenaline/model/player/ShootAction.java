package adrenaline.model.player;

import adrenaline.data.data_for_server.data_for_game.AtomicTarget;
import adrenaline.exceptions.IllegalTargetException;
import adrenaline.model.deck.AtomicWeaponEffect;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.WeaponEffect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShootAction implements Action {
    private Player attacker;
    private boolean baseUsed;
    private boolean mustUseBase;
    private Weapon chosenWeapon;
    private WeaponEffect baseEffect;
    private List<WeaponEffect> optionalEffects;
    private LinkedList<WeaponEffect> effects;
    private boolean endAction;

    public ShootAction(Player attacker) {
        this.attacker = attacker;
        this.baseUsed = false;
        this.mustUseBase = false;
        this.endAction = false;
        this.optionalEffects = new ArrayList<> ();
        this.effects = new LinkedList<> ();
    }

    public boolean isBaseUsed() {
        return this.baseUsed;
    }

    public void setMustUseBase(boolean value) {
        this.mustUseBase = value;
    }

    public boolean isMustUseBase() {
        return this.mustUseBase;
    }

    public Weapon getChosenWeapon() {
        return this.chosenWeapon;
    }

    public void setChosenWeapon(Weapon weapon) {
        this.chosenWeapon = weapon;
    }

    public void addBaseEffect(WeaponEffect effect) {
        this.baseEffect = effect;
        this.baseUsed = true;
        this.mustUseBase = false;
        this.effects.add(effect);
        if (chosenWeapon.getOptionalEffects ().isEmpty ())
            this.endAction = true;
    }

    public void addOptionalEffect(WeaponEffect effect) {
        this.optionalEffects.add (effect);
        this.effects.add(effect);
        if (optionalEffects.containsAll (chosenWeapon.getOptionalEffects ()))
            this.endAction = true;
    }

    public WeaponEffect getBaseEffect() {
        return this.baseEffect;
    }

    public WeaponEffect getOptionalEffect() {
        return this.optionalEffects.get(0);
    }

    public void setEffectTargets(List<AtomicTarget> targets) throws IllegalTargetException {
        boolean legal = true;
        if (targets.size() == 1) {
            //apply every atomic effect to target.get(0)
            //check if targets.get(0) are legal according to constraints
            legal = isAtomicTargetLegal(targets.get(0), 0);
            //apply atomic effects
            if (legal) {
                for (AtomicWeaponEffect atomicEffect : effects.getLast().getEffects())
                    applyEffectToAtomicTargets(targets.get(0), atomicEffect);
            } else {
                throw new IllegalTargetException ();
            }
        } else {
            int i = 0;
            //apply one atomic effect per target
            //check if targets are legal
            for (AtomicTarget target : targets) {
                if (! isAtomicTargetLegal (target, i)) {
                    legal = false;
                    break;
                }
                i++;
            }
            //apply atomic effect to its corresponding target
            if (legal) {
                for (int j = 0; j < targets.size (); j++)
                    applyEffectToAtomicTargets(targets.get(j), effects.getLast ().getEffects ().get (j));
            } else
                throw new IllegalTargetException ();
        }
    }

    private boolean isAtomicTargetLegal(AtomicTarget target, int index) {
        if (target.getTargetNames () == null && target.getSquareId () == -1)
            return effects.getLast ().getTargetTypes ().get(index).isCompliantTargets (attacker, null, -1);
        else if (target.getTargetNames () == null && target.getSquareId () != -1)
            return effects.getLast ().getTargetTypes ().get(index).isCompliantTargets (attacker, null, target.getSquareId());
        else if (target.getTargetNames() != null && target.getSquareId() == -1) {
            List<Player> targets = new ArrayList<>();
            for (String name : target.getTargetNames())
                targets.add(attacker.getGame().findByNickname(name));
            return effects.getLast().getTargetTypes ().get(index).isCompliantTargets(attacker, targets, -1);
        } else if (target.getTargetNames() != null && target.getSquareId() != -1) {
            List<Player> targets = new ArrayList<>();
            for (String name : target.getTargetNames())
                targets.add(attacker.getGame().findByNickname(name));
            return effects.getLast().getTargetTypes ().get(index).isCompliantTargets(attacker, targets, target.getSquareId());
        }
        return false;
    }

    private void applyEffectToAtomicTargets(AtomicTarget target, AtomicWeaponEffect effect) {
        if (target.getTargetNames ( ) == null && target.getSquareId ( ) == -1)
            effect.applyEffect (attacker, null, (Integer[]) null);
        else if (target.getTargetNames ( ) == null && target.getSquareId ( ) != -1)
            effect.applyEffect (attacker, null, target.getSquareId ( ));
        else if (target.getTargetNames ( ) != null && target.getSquareId ( ) == -1) {
            List<Player> targets = new ArrayList<> ();
            for (String name : target.getTargetNames ( )) {
                targets.add(attacker.getGame ( ).findByNickname (name));
                effect.applyEffect (attacker, attacker.getGame ( ).findByNickname (name), (Integer[]) null);
            }
            effects.getLast ().setTargets (targets);
        } else if (target.getTargetNames ( ) != null && target.getSquareId ( ) != -1) {
            List<Player> targets = new ArrayList<> ();
            for (String name : target.getTargetNames ( )) {
                targets.add(attacker.getGame ( ).findByNickname (name));
                effect.applyEffect (attacker, attacker.getGame ( ).findByNickname (name), target.getSquareId ( ));
            }
            effects.getLast ().setTargets (targets);
        }
        attacker.getOwnedWeapons ().remove (chosenWeapon);
        attacker.getBoard ().getUnloadedWeapons ().add(chosenWeapon);
    }

    public boolean isEndAction() {
        return this.endAction;
    }

    public void setEndAction(boolean value) {
        this.endAction = value;
    }
}
