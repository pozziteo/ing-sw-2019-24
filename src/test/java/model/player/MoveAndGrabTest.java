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
        MoveAndGrab moveAndGrab = new MoveAndGrab(player, player.getGame().isFinalFrenzy());

        Square newPosition = moveAndGrab.grabObject(player);

        assertEquals(initialSquare, newPosition);
        assertNull(((NormalSquare) newPosition).getPlacedTile());

        ((SpawnPoint)map.getSquare(2)).setWeapons(new Weapon(WeaponType.MACHINE_GUN));
        ((SpawnPoint)map.getSquare(2)).setWeapons(new Weapon(WeaponType.GRENADE_LAUNCHER));
        ((SpawnPoint)map.getSquare(2)).setWeapons(new Weapon(WeaponType.THOR));

        Weapon toGrab = ((SpawnPoint) map.getSquare(2)).getWeapons()[0];

        MoveAndGrab anotherGrab = new MoveAndGrab(player, player.getGame().isFinalFrenzy());
        Square anotherPosition = anotherGrab.grabObject(player, 0, 2);

        assertNotEquals(initialSquare, anotherPosition);
        assertEquals(map.getSquare(2), anotherPosition);
        assertNull(((SpawnPoint) anotherPosition).getWeapons()[0]);
        assertEquals(toGrab, player.getOwnedWeapons().get(0));
    }

    @Test
    void adrenalineMoveAndGrabTest() {
        Map map = g.getArena();
        Square initialSquare = map.getSquare(1);
        Player notAdrenaline = g.getPlayers().get(1);
        Player adrenaline = g.getPlayers().get(2);
        adrenaline.getBoard().gotHit(5, notAdrenaline);

        notAdrenaline.setPosition(initialSquare);
        adrenaline.setPosition(initialSquare);

        NormalSquare squareToGet = (NormalSquare) map.getSquare(6);
        squareToGet.setPlacedTile(new Tile(TileFormat.TILE_FORMAT_20));

        MoveAndGrab thisWillFail = new MoveAndGrab(notAdrenaline, notAdrenaline.getGame().isFinalFrenzy());
        Square samePosition = thisWillFail.grabObject(notAdrenaline, 1, 2);
        assertNotNull(squareToGet.getPlacedTile());
        assertEquals(initialSquare, samePosition);

        MoveAndGrab thisWillSucceed = new MoveAndGrab(adrenaline, adrenaline.getGame().isFinalFrenzy());
        Square newPosition = thisWillSucceed.grabObject(adrenaline, 1, 2);
        assertEquals(squareToGet, newPosition);
        assertNull(squareToGet.getPlacedTile());

    }

}