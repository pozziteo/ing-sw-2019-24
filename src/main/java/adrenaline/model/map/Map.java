package adrenaline.model.map;

import adrenaline.model.player.Player;

import java.io.Serializable;
import java.util.*;

/**
 * Map is a Singleton class which contains the description about the map currently
 * used in a game. There can be only one instance of Map in a single game
 */
public class Map implements Serializable {

    /**
     * attribute which represents the "file name" of the map
     */
    private String actualMap;

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

    public String getActualMapName() {
        return actualMap;
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
     * Method to get the array of squares that make up a map
     * @return
     */

    public Square[] getArena() {
        return this.arena;
    }


    /**
     * Method to determine which players are in the same room and can therefore see each other
     * @param color
     * @return list of players in the same room
     */

    public List<Player> getPlayersInRoom(String color){
        //TODO
        return new ArrayList<>();
    }
}
