package model.map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMap {
    @Test
    public void testMapCreation() throws Exception {
        ReadArena read = new ReadArena();
        Map m = read.createArena ();
        assertTrue(m.getSquare (0,0).getSquareColor ().equals("blue"));
    }

}
