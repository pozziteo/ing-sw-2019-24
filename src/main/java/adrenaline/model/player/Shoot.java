package adrenaline.model.player;

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

    public void setEffectTargets() {

    }

    public void setEffectTargets(List<String> targetNames) {
        //this.effects.getLast().useEffect (attacker, );
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
