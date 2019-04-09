package model.map;

import model.Game;
import model.deck.*;
import model.player.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Square is a single "element" which compose the map, it is characterized by
 * an id, a room and the links to the other squares in the map
 */
public class Square {

    /**
     * squareId is the id of a square, and also its index in arena attribute
     * of Map instance which contains it
     */
    private int squareId;

    /**
     * room is the color of the room the square belongs to
     */
    private String room;

    /**
     * spawn is a boolean which states if the square is a spawn point or not
     */
    private boolean spawn;

    /**
     * links is a list of all the other squares a player can move to from the actual square
     */
    private ArrayList<Integer> links;

    /**
     * placedTile is the Tile card actually present on the square and collectible
     */
    private Tile placedTile;

    /**
     * playersOnSquare is a list of all the players currently placed on the square
     */
    private ArrayList<Player> playersOnSquare;

    /**
     * Constructor which initialize the static attributes of a square
     * @param id is the id of a square
     * @param color is the room's color the square belongs to
     * @param sp states if the square is a spawn point or not
     * @param links refers to the ids of the square which are "linked" to the
     *              actual square
     */
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

    public int getSquareId() {
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

    /**
     * Getter method to obtain the list of squares this square is linked to
     * @return
     */

    public ArrayList<Integer> getLinks() {
        return this.links;
    }

    /**
     * Getter to obtain the current Tile placed on the square
     * @return the tile on the square
     */
    public Tile getPlacedTile() {
        return this.placedTile;
    }

    /**
     * Setter method to draw a Tile from the deck and place it on this square
     */

    public void setTile() {
        this.placedTile = (Tile) Game.getGameInstance ( ).getTilesDeck ().drawCard ();
    }

    /**
     * Getter to obtain all the players actually placed on the square
     * @return a list of players on the square
     */
    public List<Player> getPlayersOnSquare() {
        return this.playersOnSquare;
    }

    /**
     * Method to know if two squares are in the same room
     * @param s
     * @return true (if color of this square equals color of s), false (otherwise)
     */

    public boolean isInTheSameRoom(Square s) {
        if (this.getSquareColor ().equals(s.getSquareColor ())) {
            return true;
        }
        return false;
    }

    public List<Integer> getLinks() {
        return this.links;
    }
}