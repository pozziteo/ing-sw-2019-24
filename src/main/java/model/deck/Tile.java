package model.deck;

public class Tile extends Card {

    private TileFormat format;

    public Tile(TileFormat format) {
        this.format = format;
    }

    public String getTileContent() {
        return format.getDescription();
    }
}
