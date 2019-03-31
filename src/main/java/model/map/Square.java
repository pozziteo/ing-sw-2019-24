package main.java.model.map;

import main.java.model.deck.Tile;
import main.java.model.player.Player;

import java.util.ArrayList;

public class Square {
    private int coordinateX;
    private int coordinateY;
    private String color;
    private boolean spawnPoint;
    private Tile placedTile;
    private ArrayList<Player> playersOnSquare;
    private ArrayList<Door> doorsInSquare;

    Square(int x, int y, String color, boolean sp) {
        this.coordinateX = x;
        this.coordinateY = y;
        this.color = color;
        this.spawnPoint = sp;
    }

    /**
     * Getter method to know which room this square belongs to
     * @return square's color
     */

    public String getSquareColor() {
        return this.color;
    }

}
