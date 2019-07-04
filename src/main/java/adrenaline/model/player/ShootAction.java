package adrenaline.model.player;

import adrenaline.data.data_for_server.data_for_game.AtomicTarget;
import adrenaline.exceptions.IllegalTargetException;
import adrenaline.exceptions.IllegalUseOfPowerUpException;
import adrenaline.exceptions.NotEnoughAmmoException;
import adrenaline.model.deck.AtomicWeaponEffect;
import adrenaline.model.deck.OptionalEffect;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.WeaponEffect;
import adrenaline.model.deck.powerup.PowerUpEffect;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of Action interface, it contains all the Shoot Action methods
 */
public class ShootAction implements Action {
    private Player attacker;
    private boolean baseUsed;
    private boolean mustUseBase;
    private boolean adrenaline;
    private Weapon chosenWeapon;
    private WeaponEffect baseEffect;
    private List<WeaponEffect> optionalEffects;
    private LinkedList<WeaponEffect> effects;
    private boolean endAction;

    public ShootAction(Player attacker) {
        this.attacker = attacker;
        this.adrenaline = (this.attacker.getBoard ().getDamageTaken ().size () > 5);
        this.baseUsed = false;
        this.mustUseBase = false;
        this.endAction = false;
        this.optionalEffects = new ArrayList<> ();
        this.effects = new LinkedList<> ();
    }

    /**
     * Method to establish if the shoot action is an adrenalineAction
     * @return true if the shoot action is an adrenalineAction, false otherwise
     */
    public boolean isAdrenaline() {
        return this.adrenaline;
    }

    /**
     * Method to establish if a shoot action is the base action
     * @return true if it is it
     */
    public boolean isBaseUsed() {
        return this.baseUsed;
    }

    /**
     * Setter method
     * @param value is a boolean type
     */
    public void setMustUseBase(boolean value) {
        this.mustUseBase = value;
    }

    /**
     * Getter method
     * @return true if the action must use base
     */
    public boolean isMustUseBase() {
        return this.mustUseBase;
    }

    /**
     * Getter method
     * @return the chosen weapon
     */
    public Weapon getChosenWeapon() {
        return this.chosenWeapon;
    }

    /**
     * Setter method to set the chosen weapon
     * @param weapon is the weapon to set
     */
    public void setChosenWeapon(Weapon weapon) {
        this.chosenWeapon = weapon;
    }

    /**
     * Method to add a base effect of a weapon
     * @param effect is the weapon effect
     */
    public void addBaseEffect(WeaponEffect effect) {
        this.baseEffect = effect;
        this.baseUsed = true;
        this.mustUseBase = false;
        this.effects.add(effect);
        if (chosenWeapon.getOptionalEffects ().isEmpty ())
            this.endAction = true;
    }

    /**
     * Method to add optional effects to the action
     * @param effect is the optional effect
     * @throws NotEnoughAmmoException if the player does not have enough ammo for the optional effect
     */
    public void addOptionalEffect(WeaponEffect effect) throws NotEnoughAmmoException {
        if (((OptionalEffect)effect).isUsable (attacker)) {
            this.optionalEffects.add (effect);
            this.effects.add (effect);
            if (optionalEffects.containsAll (chosenWeapon.getOptionalEffects ( )) && !mustUseBase)
                this.endAction = true;
        } else
            throw new NotEnoughAmmoException ("You don't have enough ammo to use this additional effect.");
    }

    /**
     * Getter method
     * @return the base effect of this weapon
     */
    public WeaponEffect getBaseEffect() {
        return this.baseEffect;
    }

    /**
     * Getter method
     * @return the optional effect of this weapon
     */
    public WeaponEffect getOptionalEffect() {
        return this.optionalEffects.get(0);
    }

    /**
     * Setter method apply the effect
     * @param targets is the list of atomic targets
     * @param targetingScopeTarget is the target of the targetingScope user
     * @throws IllegalTargetException if the target cannot be hit
     * @throws IllegalUseOfPowerUpException is the player cannot use the targeting scope
     * @throws NotEnoughAmmoException if the players doesn't have enough ammo
     */
    public void setEffectTargets(List<AtomicTarget> targets, String targetingScopeTarget) throws IllegalTargetException, IllegalUseOfPowerUpException, NotEnoughAmmoException {
        boolean legal = true;
        if (targets.size() == 1) {
            //apply every atomic effect to target.get(0)
            //check if targets.get(0) are legal according to constraints
            legal = isAtomicTargetLegal(targets.get(0), 0);
            //apply atomic effects
            if (legal) {
                if (targetingScopeTarget != null && targets.get(0).getTargetNames ().contains(targetingScopeTarget) && attacker.hasTargetingScope ()) {
                    PowerUpEffect effect = new PowerUpEffect (attacker, attacker.findPowerUp ("Targeting Scope"));
                    effect.useTargetingScope (attacker.getGame ().findByNickname (targetingScopeTarget));
                } else if (targetingScopeTarget != null && attacker.hasTargetingScope ())
                    throw new IllegalUseOfPowerUpException ("You can't hit this player with Targeting Scope in this action.");
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

    /**
     * Method to establish if an atomic target is legal
     * @param target is the target to check
     * @param index is the index
     * @return true if he is legal, false otherwise
     */
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

    /**
     * Method to apply the effect to an atomic target
     * @param target is the target
     * @param effect is the effect to apply
     */
    private void applyEffectToAtomicTargets(AtomicTarget target, AtomicWeaponEffect effect) {
        if (target.getTargetNames ( ) == null && target.getSquareId ( ) == -1)
            effect.applyEffect (attacker, null, (Integer[]) null);
        else if (target.getTargetNames ( ) == null && target.getSquareId ( ) != -1)
            effect.applyEffect (attacker, null, target.getSquareId ( ));
        else if (target.getTargetNames ( ) != null && target.getSquareId ( ) == -1) {
            List<Player> targets = new ArrayList<> ();
            for (String name : target.getTargetNames ( )) {
                Player targetPlayer = attacker.getGame ( ).findByNickname (name);
                if (targetPlayer.getBoard ().getDamageTaken ().size() < 12) {
                    targets.add(targetPlayer);
                    effect.applyEffect (attacker, attacker.getGame ( ).findByNickname (name), (Integer[]) null);
                }
            }
            effects.getLast ().setTargets (targets);
        } else if (target.getTargetNames ( ) != null && target.getSquareId ( ) != -1) {
            List<Player> targets = new ArrayList<> ();
            for (String name : target.getTargetNames ( )) {
                Player targetPlayer = attacker.getGame ( ).findByNickname (name);
                if (targetPlayer.getBoard ().getDamageTaken ().size() < 12) {
                    targets.add(targetPlayer);
                    effect.applyEffect (attacker, attacker.getGame ( ).findByNickname (name), target.getSquareId ());
                }
            }
            effects.getLast ().setTargets (targets);
        }
        if (! attacker.getBoard ().getUnloadedWeapons ().contains (chosenWeapon)) {
            attacker.getOwnedWeapons ( ).remove (chosenWeapon);
            attacker.getBoard ( ).setUnloadedWeapons (chosenWeapon);
        }
    }

    /**
     * Method to check if an action is ending
     * @return true if it is ending
     */
    public boolean isEndAction() {
        return this.endAction;
    }

    /**
     * Setter method to set the ending action attribute
     * @param value is true if the action needs to end, false otherwise
     */
    public void setEndAction(boolean value) {
        this.endAction = value;
    }
}
