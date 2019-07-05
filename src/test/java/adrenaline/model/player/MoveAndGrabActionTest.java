package adrenaline.model.player;

import adrenaline.exceptions.MustDiscardWeaponException;
import adrenaline.exceptions.NotEnoughAmmoException;
import adrenaline.model.Game;
import adrenaline.model.deck.Tile;
import adrenaline.model.deck.TileFormat;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.WeaponType;
import adrenaline.model.map.Map;
import adrenaline.model.map.NormalSquare;
import adrenaline.model.map.SpawnPoint;
import adrenaline.model.map.Square;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MoveAndGrabActionTest {
    private static final String PATH = "/maps/";
    private static final String SMALL = PATH + "smallmap.json";
    private String[] playerNames = {"luca", "matteo", "sara"};
    private Game g = new Game(playerNames);

    @Test
    void moveAndGrabTest() {
        this.g.setArena (SMALL);
        Map map = g.getMap ();
        Square initialSquare = map.getSquare(1);
        ((NormalSquare) initialSquare).setPlacedTile(new Tile(TileFormat.TILE_FORMAT_4));
        Player player = g.getPlayers().get(0);
        player.setPosition(initialSquare);
        MoveAndGrabAction moveAndGrab = new MoveAndGrabAction (player, player.getGame().isFinalFrenzy());

        try {
            Square newPosition = moveAndGrab.grabObject (player, null);
            assertEquals(initialSquare, newPosition);
            assertNull(((NormalSquare) newPosition).getPlacedTile());
        } catch (NotEnoughAmmoException | MustDiscardWeaponException e){
            System.out.print (e.getMessage ());
        }

        ((SpawnPoint)map.getSquare(2)).setWeapons(new Weapon(WeaponType.MACHINE_GUN));
        ((SpawnPoint)map.getSquare(2)).setWeapons(new Weapon(WeaponType.GRENADE_LAUNCHER));
        ((SpawnPoint)map.getSquare(2)).setWeapons(new Weapon(WeaponType.THOR));

        Weapon toGrab = ((SpawnPoint) map.getSquare(2)).getWeapons()[0];

        MoveAndGrabAction anotherGrab = new MoveAndGrabAction (player, player.getGame().isFinalFrenzy());

        try {
            Square anotherPosition = anotherGrab.grabObject (player, 2, toGrab);

            assertNotEquals (initialSquare, anotherPosition);
            assertEquals (map.getSquare (2), anotherPosition);
            assertNull (((SpawnPoint) anotherPosition).getWeapons ( )[0]);
            assertEquals (toGrab, player.getOwnedWeapons ( ).get (0));
        } catch (NotEnoughAmmoException | MustDiscardWeaponException e) {
            System.out.print(e.getMessage ());
        }
    }

    @Test
    void adrenalineMoveAndGrabTest() {
        this.g.setArena (SMALL);
        Map map = g.getMap ();
        Square initialSquare = map.getSquare(1);
        Player notAdrenaline = g.getPlayers().get(1);
        Player adrenaline = g.getPlayers().get(2);

        notAdrenaline.setPosition(initialSquare);
        adrenaline.setPosition(initialSquare);

        adrenaline.getBoard().gotHit(5, notAdrenaline);
        NormalSquare squareToGet = (NormalSquare) map.getSquare(6);
        squareToGet.setPlacedTile(new Tile(TileFormat.TILE_FORMAT_20));

        try {
            MoveAndGrabAction thisWillFail = new MoveAndGrabAction (notAdrenaline, notAdrenaline.getGame ( ).isFinalFrenzy ( ));
            Square samePosition = thisWillFail.grabObject (notAdrenaline, 6, null);
            assertNotNull (squareToGet.getPlacedTile ( ));
            assertEquals (initialSquare, samePosition);
        } catch (NotEnoughAmmoException | MustDiscardWeaponException e) {
            System.out.print (e.getMessage ());
        }

        try {
            MoveAndGrabAction thisWillSucceed = new MoveAndGrabAction (adrenaline, adrenaline.getGame ( ).isFinalFrenzy ( ));
            Square newPosition = thisWillSucceed.grabObject (adrenaline, 6, null);
            assertEquals (squareToGet, newPosition);
            assertNull (squareToGet.getPlacedTile ( ));
        } catch (NotEnoughAmmoException | MustDiscardWeaponException e) {
            System.out.print (e.getMessage ());
        }

    }

}