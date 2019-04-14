package model.player;

import model.Game;
import model.deck.*;
import model.map.*;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Game game = new Game(3);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);
    private Player p3 = game.getPlayers ().get(2);;

    @Test
    void testGiveMarks() throws Exception {
        p1.setPosition(game.getArena ().getSquare(2));
        p2.setPosition(game.getArena ().getSquare(1));

        p1.giveMark (3, p2);
        assertEquals(3, p2.getBoard().getMarksAmountGivenByPlayer (p1));
    }

    @Test
    public void testPlayersInSameRoom() {
        p1.setPosition(game.getArena().getSquare(0));
        p2.setPosition(game.getArena().getSquare(1));

        assertTrue(p1.isInTheSameRoom (p2));
    }

    @Test
    public void testPlayerCanSee() {
        p1.setPosition(game.getArena().getSquare(0));
        p2.setPosition(game.getArena().getSquare(6));

        assertTrue(p1.canSee (p2));
    }
}
