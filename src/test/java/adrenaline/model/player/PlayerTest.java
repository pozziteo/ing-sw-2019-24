package adrenaline.model.player;

import adrenaline.model.Game;
import adrenaline.model.deck.powerup.PowerUp;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";

    private String[] playerNames = {"luca", "matteo", "sara", "cipro", "sally"};
    private Game game = new Game(playerNames);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);
    private Player p3 = game.getPlayers ().get(2);
    private Player p4 = game.getPlayers().get(3);
    private Player p5 = game.getPlayers().get(4);

    @Test
    void testGiveMarks() {
        this.game.setArena (SMALL);
        p1.setPosition(game.getMap ().getSquare(2));
        p2.setPosition(game.getMap ().getSquare(1));

        p1.giveMark (3, p2);
        assertEquals(3, p2.getBoard().getMarksAmountGivenByPlayer (p1));
    }

    @Test
    public void testPlayersInSameRoom() {
        this.game.setArena (SMALL);
        p1.setPosition(game.getMap ().getSquare(0));
        p2.setPosition(game.getMap ().getSquare(1));

        assertTrue(p1.isInTheSameRoom (p2));
    }

    @Test
    public void testPlayerCanSee() {
        this.game.setArena (SMALL);
        p1.setPosition(game.getMap ().getSquare(0));
        p2.setPosition(game.getMap ().getSquare(6));

        assertTrue(p1.canSee (p2));
    }

    @Test
    public void testPlayerChooseSpawn() {
        this.game.setArena (SMALL);
        PowerUp powerup = p1.getOwnedPowerUps ().get(0);
        p1.chooseSpawnPoint (powerup.getAmmo().getColor());
        p1.getPosition ();
        assertTrue(p1.getPosition ().isSpawnPoint ());
        assertTrue(p1.getPosition ().getSquareColor ().equals(powerup.getAmmo().getColor ()));
    }

    @Test
    void deathTest(){
        assertEquals(0, p1.getDeaths());

        p1.getBoard().gotHit(12, p2);
        assertEquals(1, p2.getBoard().getReceivedMarks().size());
        assertEquals(9, p2.getPointTokens());
        assertEquals(1, p1.getDeaths());

        p1.getBoard().gotHit(2, p3);
        p1.getBoard().gotHit(7, p4);
        p1.getBoard().gotHit(3, p5);
        assertEquals(3, p3.getPointTokens());
        assertEquals(6, p4.getPointTokens());
        assertEquals(4, p5.getPointTokens());
        assertEquals(1, p5.getBoard().getReceivedMarks().size());
        assertEquals(2, p1.getDeaths());
    }
}
