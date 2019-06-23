package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;
import java.util.List;

public class NormalSquareDetails extends SquareDetails implements Serializable {
    private String tileOnSquare;
    private String tileFormat;

    public NormalSquareDetails(int id, List<String> players, String tile, String tileFormat) {
        super(id, players, false);
        this.tileOnSquare = tile;
        this.tileFormat = tileFormat;
    }

    public String getTileOnSquare() {
        return this.tileOnSquare;
    }

    public String getTileFormat() {
        return this.tileFormat;
    }
}
