package model.map;

import model.player.*;

import java.io.FileNotFoundException;
import java.util.*;

public class Map {
    private int dimension;
    private Square[] arena;

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
