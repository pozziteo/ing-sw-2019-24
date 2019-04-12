package model.map;

import model.deck.Tile;

import java.util.List;

public class NormalSquare extends Square {

    /**
     * placedTile is the Tile card actually present on the square and collectible
     */
    private Tile placedTile;

    /**
     * Getter to obtain the current Tile placed on the square
     * @return the tile on the square
     */
    public Tile getPlacedTile() {
        return this.placedTile;
    }

    /**
     * Setter method to place a tile on this square
     * @param t
     */

    public void setPlacedTile(Tile t) {
        this.placedTile = t;
    }

    public NormalSquare(int id, String color, List<Integer> links) {
        super(id, color, false, links);
        placedTile = null;
    }

}
