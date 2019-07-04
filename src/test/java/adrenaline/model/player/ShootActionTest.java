package adrenaline.model.player;

import adrenaline.model.Game;
import adrenaline.model.deck.Weapon;
import org.junit.jupiter.api.Test;

class ShootActionTest {
    private String[] playerNames = {"luca", "matteo", "sara"};
    private Game g = new Game(playerNames);
    private Player p = g.getPlayers ().get(0);

    @Test
    public void testPlayerShoot() {
        p.getOwnedWeapons ().add((Weapon) g.getWeaponsDeck ().drawCard ());
        p.getOwnedWeapons ().add((Weapon) g.getWeaponsDeck ().drawCard ());
       // ShootAction shoot = new ShootAction(p);
    }
}
