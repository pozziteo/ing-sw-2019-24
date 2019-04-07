package model.map;

import model.player.*;

import java.io.FileNotFoundException;
import java.util.*;

public class Map {
    private static Map map;
    private int dimension;
    private Square[] arena;

    private Map() {
    }

    public static Map getInstance(String fileName) throws FileNotFoundException {
        if (map == null)
            map = (new ArenaBuilder()).createMap(fileName);
        return map;
    }

    public int getDimension() {
        return this.dimension;
    }

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
