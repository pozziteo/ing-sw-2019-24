package model.deck;

import model.player.Player;

public interface AtomicWeaponEffect {
    void applyEffect(Player attacker, Player target, Integer... id);
}
