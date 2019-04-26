package adrenaline.model.player;

import adrenaline.model.Game;
import adrenaline.model.map.Map;
import adrenaline.model.map.Square;
import adrenaline.model.player.Move;
import adrenaline.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {
     private Game g = new Game(3);

     @Test
    void correctMovesTest() {
        Map map = g.getMap ();
        Player player = g.getPlayers().get(0);
        player.setPosition(map.getSquare(2));
        Move move = new Move(player, player.getGame().isFinalFrenzy());

        //Testing an invalid move: asserting player hasn't changed position
        //newPosition should be equal to InitialPosition
        Square initialPosition = player.getPosition();
        Square newPosition = move.takeMove(player, 2, 2);
        assertFalse(move.getPaths().contains(10)); //10 is the squareId of (2, 2)
        assertEquals(initialPosition, newPosition);

        //Now testing a valid move: player should get the new position
        Move anotherMove = new Move(player, player.getGame().isFinalFrenzy());
        Square anotherPosition = move.takeMove(player, 0, 0);
        assertTrue(anotherMove.getPaths().contains(0));
        assertNotEquals(initialPosition, anotherPosition);
        assertEquals(0, player.getPosition().getSquareId());
    }

}