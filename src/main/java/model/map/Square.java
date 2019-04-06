package model.map;

import model.deck.*;
import model.player.*;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

public class Square implements Serializable {
    private int squareId;
    private String room;
    private boolean spawn;
    private ArrayList<Integer> links;
    private Tile placedTile;
    private ArrayList<Player> playersOnSquare;

    public Square(int id, String color, boolean sp, List<Integer> links) {
        this.squareId = id;
        this.room = color;
        this.spawn = sp;
        this.links = new ArrayList<>();
        this.links.addAll(links);
    }

    /**
     * Getter method to know a square's Id
     * @return Id
     */

    public int getId() {
        return this.squareId;
    }
    /**
     * Getter method to know a square's x coordinate
     * @return x
     */

    public int getX() {
        return this.squareId/4;
    }

    /**
     * Getter method to know a square's y coordinate
     * @return y
     */

    public int getY() {
        return this.squareId%4;
    }

    /**
     * Getter method to know which room this square belongs to
     * @return square's color
     */

    public String getSquareColor() {
        return this.room;
    }

    public Tile getPlacedTile() {
        return placedTile;
    }

    public List<Player> getPlayersOnSquare() {
        return playersOnSquare;
    }
}