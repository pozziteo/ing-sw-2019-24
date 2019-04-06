package model.map;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ArenaBuilderTest {

    @Test
    void createMapTest() {
        try {
            ArenaBuilder builder = new ArenaBuilder();
            Map map = builder.createMap("smallmap.json");
            assertEquals(map.getSquare(6).getSquareColor(), "red");
        } catch (FileNotFoundException exc) {
            System.err.println("Error: Invalid Map name selected");
            exc.printStackTrace();
        }
    }
}