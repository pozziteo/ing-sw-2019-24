package adrenaline.model.deck;

import adrenaline.model.player.Player;

/**
 * Interface of a weapon effect, implemented by other classes
 */
public interface AtomicWeaponEffect {
    void applyEffect(Player attacker, Player target, Integer... id);
}
