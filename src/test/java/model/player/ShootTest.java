package model.player;

import model.Game;
import model.map.*;
import model.deck.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
