package model.map;

import model.deck.*;
import model.player.*;

import java.util.ArrayList;

public class Square {
    private int coordinateX;
    private int coordinateY;
    private String color;
    private boolean spawnPoint;
    private Tile placedTile;
    private ArrayList<Player> playersOnSquare;
    private ArrayList<Door> doorsInSquare;

    public Square(int x, int y, String color, boolean sp) {
        this.coordinateX = x;
        this.coordinateY = y;
        this.color = color;
        this.spawnPoint = sp;
    }

    /**
     * Getter method to know a square's x coordinate
     * @return x
     */

    public int getX() {
        return this.coordinateX;
    }

    /**
     * Getter method to know a square's y coordinate
     * @return y
     */

    public int getY() {
        return this.coordinateY;
    }

    /**
     * Getter method to know which room this square belongs to
     * @return square's color
     */

    public String getSquareColor() {
        return this.color;
    }

}
