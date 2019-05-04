package adrenaline.model.deck;

import adrenaline.model.player.Player;

public interface AtomicWeaponEffect {
    void applyEffect(Player attacker, Player target, Integer... id);
}
