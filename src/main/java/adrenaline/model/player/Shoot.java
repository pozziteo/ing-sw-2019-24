package adrenaline.model.player;

import adrenaline.model.deck.BaseEffect;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.WeaponEffect;

import java.util.List;

public class Shoot implements Action {
    private boolean baseUsed;
    private Weapon chosenWeapon;
    private List<WeaponEffect> effects;
    private boolean endAction;

    public Shoot() {
        this.baseUsed = false;
        this.endAction = false;
    }

    public void setBaseUsed(boolean value) {
        this.baseUsed = value;
    }

    public Weapon getChosenWeapon() {
        return this.chosenWeapon;
    }

    public void setChosenWeapon(Weapon weapon) {
        this.chosenWeapon = weapon;
    }

    public void addEffectToApply(WeaponEffect effect, boolean isBase) {
        this.effects.add(effect);
        this.baseUsed = isBase;
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
