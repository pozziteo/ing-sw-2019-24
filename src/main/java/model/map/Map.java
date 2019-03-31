package model.map;

import model.player.*;

import java.util.ArrayList;

public class Map {
    private Square[][] arena;

    public Map() {
        this.arena = new Square[4][3];
    }

    /**
     * Method to determine which players are in the same room and can therefore see each other
     * @param color
     * @return list of players in the same room
     */

    public ArrayList<Player> getPlayersInRoom(String color){

    }
}
