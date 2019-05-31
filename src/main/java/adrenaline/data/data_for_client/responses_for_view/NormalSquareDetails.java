package adrenaline.data.data_for_client.responses_for_view;

import java.io.Serializable;
import java.util.List;

public class NormalSquareDetails extends SquareDetails implements Serializable {
    private String tileOnSquare;

    public NormalSquareDetails(int id, List<String> players, String tile) {
        super(id, players, false);
        this.tileOnSquare = tile;
    }

    public String getTileOnSquare() {
        return this.tileOnSquare;
    }
}
