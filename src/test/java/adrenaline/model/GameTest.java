package adrenaline.model;

import adrenaline.model.deck.Weapon;
import adrenaline.model.map.NormalSquare;
import adrenaline.model.map.SpawnPoint;
import adrenaline.model.map.Square;
import adrenaline.model.player.MoveAction;
import adrenaline.model.player.MoveAndGrabAction;
import adrenaline.model.player.Player;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";
    private String[] playerNames = {"luca", "matteo", "sara", "foo", "bar"};
    private Game game = new Game (playerNames);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);
    private Player p3 = game.getPlayers ().get(2);
    private Player p4 = game.getPlayers ().get(3);
    private Player p5 = game.getPlayers ().get(4);

    @Test
    void testID() {
        assertTrue (this.game.getGameID () != (new Random().nextInt() * 1000000));
    }

    @Test
    void testStartGame() {
        game.setArena (SMALL);
        game.startGame ();
        assertTrue(game.isStartGame ());
        for (Square s : game.getMap ().getArena ()) {
            if (s.isSpawnPoint ()) {
                assertEquals (3, ((SpawnPoint) s).getWeapons ( ).length);
            } else
                assertNotNull (((NormalSquare) s).getPlacedTile ());
        }
    }

    @Test
    void testEndGame() {
        game.setEndGame (true);
        assertTrue(game.isEndGame ());
    }

    @Test
    void testIncrementTurn() {
        game.setArena (SMALL);
        game.startGame ();
        game.incrementTurn ();
        assertEquals(1, game.getCurrentTurn ());

        game.incrementTurn ();
        game.incrementTurn ();
        game.incrementTurn ();
        game.incrementTurn ();
        assertEquals(0, game.getCurrentTurn ());
    }

    @Test
    void testActions() {
        game.setArena (SMALL);
        game.startGame ();
        p1.setPosition(game.getMap ().getSquare(2));
        game.setCurrentAction (new MoveAction (p1, game.isFinalFrenzy ()));
        game.setCurrentAction (new MoveAction (p1, game.isFinalFrenzy ()));
        game.setCurrentAction (new MoveAction (p1, game.isFinalFrenzy ()));
        assertTrue(game.getCurrentAction () instanceof MoveAction);
        game.updateCurrentAction (new MoveAndGrabAction (p1, game.isFinalFrenzy ()));
        assertTrue(game.getCurrentAction () instanceof MoveAndGrabAction);
        assertEquals(2, game.getCurrentTurnActionNumber ());
        game.incrementTurn ();
        assertEquals(0, game.getCurrentTurnActionNumber ());
    }

    @Test
    void testFindByNickname() {
        assertEquals (p5, game.findByNickname ("luca"));
    }

    @Test
    void testReplaceWeapon() {
        game.setArena (SMALL);
        game.startGame ();
        p1.setPosition (game.getMap ().getSquare (2));
        Weapon weaponToReplace = (Weapon) game.getWeaponsDeck ().drawCard ();
        assertNotEquals(weaponToReplace, ((SpawnPoint) p1.getPosition ()).getWeapons ()[0]);
        assertNotEquals(weaponToReplace, ((SpawnPoint) p1.getPosition ()).getWeapons ()[1]);
        assertNotEquals(weaponToReplace, ((SpawnPoint) p1.getPosition ()).getWeapons ()[2]);
        ((SpawnPoint) p1.getPosition ()).removeWeapon (((SpawnPoint) p1.getPosition ()).getWeapons ()[1]);
        game.replaceWeapon (p1, weaponToReplace);
        assertEquals (weaponToReplace, ((SpawnPoint) p1.getPosition ()).getWeapons ()[1]);
    }

    @Test
    void testPoints(){
        this.game.setArena (SMALL);
        p1.setPosition(game.getMap ().getSquare(2));
        p2.setPosition(game.getMap ().getSquare(4));
        p3.setPosition(game.getMap ().getSquare(6));
        p4.setPosition(game.getMap ().getSquare(8));
        p5.setPosition(game.getMap ().getSquare(10));

        p1.getBoard().gotHit(3, p2);
        p1.getBoard().gotHit(2, p3);
        p1.getBoard().gotHit(5, p4);
        p1.getBoard().gotHit(1, p5);
        game.givePoints(p1);
        assertEquals(8, p4.getBoard().getPointTokens());
        assertEquals(7, p2.getBoard().getPointTokens());
        assertEquals(4, p3.getBoard().getPointTokens());
        assertEquals(2, p5.getBoard().getPointTokens());
    }

    @Test
    void testWinner() {
        this.game.setArena (SMALL);
        p1.setPosition(game.getMap ().getSquare(2));
        p2.setPosition(game.getMap ().getSquare(4));
        p3.setPosition(game.getMap ().getSquare(11));
        assertEquals (0, game.getWinner ( ).getBoard().getPointTokens ());
        p1.getBoard().addPointTokens (8);
        p2.getBoard().addPointTokens (2);
        p3.getBoard().addPointTokens (16);
        p4.getBoard().addPointTokens (4);
        p5.getBoard().addPointTokens (6);
        game.updateRanking();
        assertEquals (16, game.getWinner ( ).getBoard().getPointTokens ());
        assertEquals (8, game.getRanking ().get(1).getBoard().getPointTokens ());
        assertEquals (6, game.getRanking ().get(2).getBoard().getPointTokens ());
        assertEquals (4, game.getRanking ().get(3).getBoard().getPointTokens ());
        assertEquals (2, game.getRanking ().get(4).getBoard().getPointTokens ());
    }

}
