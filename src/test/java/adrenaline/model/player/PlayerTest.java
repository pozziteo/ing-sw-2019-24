package adrenaline.model.player;

import adrenaline.model.Game;
import adrenaline.model.deck.PowerUp;
import adrenaline.model.player.Player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Game game = new Game(3);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);
    private Player p3 = game.getPlayers ().get(2);

    @Test
    void testGiveMarks() {
        p1.setPosition(game.getMap ().getSquare(2));
        p2.setPosition(game.getMap ().getSquare(1));

        p1.giveMark (3, p2);
        assertEquals(3, p2.getBoard().getMarksAmountGivenByPlayer (p1));
    }

    @Test
    public void testPlayersInSameRoom() {
        p1.setPosition(game.getMap ().getSquare(0));
        p2.setPosition(game.getMap ().getSquare(1));

        assertTrue(p1.isInTheSameRoom (p2));
    }

    @Test
    public void testPlayerCanSee() {
        p1.setPosition(game.getMap ().getSquare(0));
        p2.setPosition(game.getMap ().getSquare(6));

        assertTrue(p1.canSee (p2));
    }

    @Test
    public void testPlayerChooseSpawn() {
        PowerUp powerup = p1.getOwnedPowerUps ().get(0);
        p1.chooseSpawnPoint (powerup);
        p1.getPosition ();
        assertTrue(p1.getPosition ().isSpawnPoint ());
        assertTrue(p1.getPosition ().getSquareColor ().equals(powerup.getType ().getColor ()));
    }
}
