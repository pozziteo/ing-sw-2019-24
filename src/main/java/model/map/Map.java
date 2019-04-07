package model.map;

import model.player.*;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Map is a Singleton class which contains the description about the map currently
 * used in a game. There can be only one instance of Map in a single game
 */
public class Map {

    /**
     * map is the instance of Map currently used in the game
     */
    private static Map map;

    /**
     * dimension is the number of squares which compose the map
     */
    private int dimension;

    /**
     * arena is a static array which contains the squares of the map
     */
    private Square[] arena;

    /**
     * Constructor is private to avoid the creation of other Map instances,
     * and never used
     */
    private Map() {
    }

    /**
     * Method to get the instance of Map currently used in the game. If it doesn't exist,
     * it creates one and stores it
     * @param fileName is the file's name to get the description of the map from
     * @return an instance of Map
     * @throws FileNotFoundException if the file's name is illegal
     */
    public static Map getInstance(String fileName) throws FileNotFoundException {
        if (map == null)
            map = (new ArenaBuilder()).createMap(fileName);
        return map;
    }

    /**
     * Getter to obtain the dimension in squares of the map
     * @return the map's number of squares
     */
    public int getDimension() {
        return this.dimension;
    }

    /**
     * Getter to obtain a single Square of the map
     * @param id is the index of the square in arena attribute
     * @return the square selected
     */
    public Square getSquare(int id) {
        return this.arena[id];
    }

    /**
     * Method to determine which players are in the same room and can therefore see each other
     * @param color
     * @return list of players in the same room
     */

   /* public ArrayList<Player> getPlayersInRoom(String color){

    } */
}
