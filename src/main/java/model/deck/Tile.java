package model.deck;

import java.util.*;

/**
 * Tile is a Card which contains Ammos and (optionally) a PowerUp
 * collectible on the game board when a player is located on a square
 * which contains one
 */
public class Tile extends Card {

    /**
     * Format is the particular format of the Tile, which describes
     * its content
     */
    private TileFormat format;

    protected Tile() {
        super();
    }

    /**
     * Constructor which initialize the format of a Tile
     * @param format describes the content of a Tile
     */
    public Tile(TileFormat format) {
        this.format = format;
    }

    public TileFormat getFormat() {
        return this.format;
    }

    /**
     * Getter to obtain a description about the content of a Tile
     * @return the content of a Tile
     */
    public String getTileDescription() {
        return this.getFormat ().getDescription();
    }

    /**
     * Getter method to obtain the list of ammo in a tile
     * @return a list containing ammo in the Tile
     */

    public List<Ammo> getTileContent() {

        return this.getFormat ().getAmmo ();
    }

}
