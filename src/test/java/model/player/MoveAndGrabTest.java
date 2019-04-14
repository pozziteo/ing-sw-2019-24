package model.player;

import model.Game;
import model.deck.Tile;
import model.deck.TileFormat;
import model.deck.Weapon;
import model.deck.WeaponType;
import model.map.Map;
import model.map.NormalSquare;
import model.map.SpawnPoint;
import model.map.Square;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveAndGrabTest {

    private Game g = new Game(3);

    //TODO add JavaDoc
    @Test
    void moveAndGrabTest() {
        Map map = g.getArena();
        Square initialSquare = map.getSquare(1);
        ((NormalSquare) initialSquare).setPlacedTile(new Tile(TileFormat.TILE_FORMAT_4));
        Player player = g.getPlayers().get(0);
        player.setPosition(initialSquare);
        MoveAndGrab moveAndGrab = new MoveAndGrab(player);

        Square newPosition = moveAndGrab.grabObject(player);

        assertEquals(initialSquare, newPosition);
        assertNull(((NormalSquare) newPosition).getPlacedTile());

        ((SpawnPoint)map.getSquare(2)).setWeapons(new Weapon(WeaponType.MACHINE_GUN));
        ((SpawnPoint)map.getSquare(2)).setWeapons(new Weapon(WeaponType.GRENADE_LAUNCHER));
        ((SpawnPoint)map.getSquare(2)).setWeapons(new Weapon(WeaponType.THOR));

        Weapon toGrab = ((SpawnPoint) map.getSquare(2)).getWeapons()[0];

        MoveAndGrab anotherGrab = new MoveAndGrab(player);
        Square anotherPosition = anotherGrab.grabObject(player, 0, 2);

        assertNotEquals(initialSquare, anotherPosition);
        assertEquals(map.getSquare(2), anotherPosition);
        assertNull(((SpawnPoint) anotherPosition).getWeapons()[0]);
        assertEquals(toGrab, player.getOwnedWeapons().get(0));
    }

}