package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains information for normal squares shown to the client
 */

public class NormalSquareDetails extends SquareDetails implements Serializable {
    private String tileOnSquare;
    private String tileFormat;

    public NormalSquareDetails(int id, List<String> players, String tile, String tileFormat) {
        super(id, players, false);
        this.tileOnSquare = tile;
        this.tileFormat = tileFormat;
    }

    /**
     * Getter method for tile on square
     * @return tile on square
     */

    public String getTileOnSquare() {
        return this.tileOnSquare;
    }

    /**
     * Getter method for tile format
     * @return tile format
     */

    public String getTileFormat() {
        return this.tileFormat;
    }
}
