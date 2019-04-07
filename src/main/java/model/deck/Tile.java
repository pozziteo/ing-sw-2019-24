package model.deck;

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

    /**
     * Constructor which initialize the format of a Tile
     * @param format describes the content of a Tile
     */
    public Tile(TileFormat format) {
        this.format = format;
    }

    /**
     * Getter to obtain a description about the content of a Tile
     * @return the content of a Tile
     */
    public String getTileContent() {
        return format.getDescription();
    }
}
