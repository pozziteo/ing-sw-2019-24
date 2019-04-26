package adrenaline.model.deck;

public class TilesDeckCreator extends DeckCreator {

    /**
     * Calls superclass constructor
     */

    public TilesDeckCreator() {
        super();
    }

    /**
     * This method implements the abstract method createDeck() in DeckCreator abstract class
     * @return a deck of tiles
     */

    public TilesDeck createDeck() {
        TilesDeck tilesDeck = new TilesDeck();

        for (TileFormat format : TileFormat.values()) {
            Tile tile = new Tile(format);
            tilesDeck.addCard(tile);
        }

        tilesDeck.deckShuffle ();

        return tilesDeck;
    }
}
