package model.map;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ArenaBuilderTest {

    @Test
    void createMapTest() {
        try {
//            ArenaBuilder builder = new ArenaBuilder();
//            Map map = builder.createMap("smallmap.json");
            Map map = Map.getInstance("maps\\smallmap.json");
            assertEquals("red", map.getSquare(6).getSquareColor());
            assertThrows(IndexOutOfBoundsException.class, () -> map.getSquare(map.getDimension()));
        } catch (FileNotFoundException exc) {
            System.err.println("Error: Invalid Map name selected");
            exc.printStackTrace();
        }
    }
}