package model.map;

import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

class ArenaBuilderTest {

    @Test
    void createMapTest() {
        try {
            ArenaBuilder arena = new ArenaBuilder();
            Map map;
            map = arena.createMap("smallmap.json");
            assertEquals(map.getSquare(5).getSquareColor(), "red");
        } catch (FileNotFoundException exc) {
            System.err.println("Error: Invalid Map file selected");
            exc.printStackTrace();
        }
    }
}