package adrenaline.model.player;

import adrenaline.model.Game;
import adrenaline.model.deck.Weapon;
import adrenaline.model.player.Player;
import adrenaline.model.player.Shoot;
import org.junit.jupiter.api.Test;

public class ShootTest {
    private Game g = new Game(3);
    private Player p = g.getPlayers ().get(0);

    @Test
    public void testPlayerShoot() {
        p.getOwnedWeapons ().add((Weapon) g.getWeaponsDeck ().drawCard ());
        p.getOwnedWeapons ().add((Weapon) g.getWeaponsDeck ().drawCard ());
        Shoot shoot = new Shoot(p);
    }
}
