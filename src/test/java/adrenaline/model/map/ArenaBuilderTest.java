package adrenaline.model.map;

import adrenaline.model.deck.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ArenaBuilderTest {

    private static Map map;
    private static final String PATH ="/maps/";
    private static final String SMALL = PATH +"smallmap.json";

    @BeforeAll
    static void createMap() {
        try {
        map = new ArenaBuilder ().createMap(SMALL);
        } catch (FileNotFoundException exc) {
            System.err.println("Error: Invalid map name selected");
            exc.printStackTrace();
        }
    }

    @Test
    void createMapTest() {
        assertEquals("Red", map.getSquare(6).getSquareColor());
        assertThrows(IndexOutOfBoundsException.class, () -> map.getSquare(map.getDimension()));
    }

    @Test
    void checkSpawnPoint() {
        assertTrue(map.getSquare(2) instanceof SpawnPoint);
        assertTrue(map.getSquare(9) instanceof NormalSquare);
    }

    @Test
    void checkUseOfSquares() {
        assertThrows(ClassCastException.class, () -> { Square square = map.getSquare(2);
            Tile tile = ((NormalSquare) square).getPlacedTile(); });
        assertThrows(ClassCastException.class, () -> { Square square = map.getSquare(0);
            ((SpawnPoint) square).getWeapons(); } );
    }
}