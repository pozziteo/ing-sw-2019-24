package adrenaline.model.player;

import adrenaline.model.Game;
import adrenaline.model.deck.Weapon;
import adrenaline.model.player.Player;
import adrenaline.model.player.Shoot;
import org.junit.jupiter.api.Test;

public class ShootTest {
    private String[] playerNames = {"luca", "matteo", "sara"};
    private Game g = new Game(playerNames);
    private Player p = g.getPlayers ().get(0);

    @Test
    public void testPlayerShoot() {
        p.getOwnedWeapons ().add((Weapon) g.getWeaponsDeck ().drawCard ());
        p.getOwnedWeapons ().add((Weapon) g.getWeaponsDeck ().drawCard ());
       // Shoot shoot = new Shoot(p);
    }
}
