package model.player;

import model.map.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {
    Player p1;
    Player p2;
    Map m;

    @Test
    public void testGiveMarks() throws Exception {
        ArenaReader reader = new ArenaReader();
        m = reader.createArena ();
        p1 = new Player("red", m.getSquare(2, 0));
        p2 = new Player("blue", m.getSquare(1, 0));
        p1.giveMark (3, p2);
        assertEquals(p2.getBoard().getAmountGivenByPlayer (p1), 3);
    }
}
