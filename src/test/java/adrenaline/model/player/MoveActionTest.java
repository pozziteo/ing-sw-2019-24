package adrenaline.model.player;

import adrenaline.model.Game;
import adrenaline.model.map.Map;
import adrenaline.model.map.Square;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MoveActionTest {
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";
    private String[] playerNames = {"luca", "matteo", "sara"};
    private Game g = new Game(playerNames);

     @Test
    void correctMovesTest() {
         this.g.setArena (SMALL);
        Map map = g.getMap ();
        Player player = g.getPlayers().get(0);
        player.setPosition(map.getSquare(2));
        MoveAction move = new MoveAction (player, player.getGame().isFinalFrenzy());

        //Testing an invalid move: asserting player hasn't changed position
        //newPosition should be equal to InitialPosition
        Square initialPosition = player.getPosition();
        Square newPosition = move.performMovement (player, 10);
        assertFalse(move.getPaths().contains(10));
        assertEquals(initialPosition, newPosition);

        //Now testing a valid move: player should get the new position
        MoveAction anotherMove = new MoveAction (player, player.getGame().isFinalFrenzy());
        Square anotherPosition = move.performMovement (player, 0);
        assertTrue(anotherMove.getPaths().contains(0));
        assertNotEquals(initialPosition, anotherPosition);
        assertEquals(0, player.getPosition().getSquareId());
    }
}