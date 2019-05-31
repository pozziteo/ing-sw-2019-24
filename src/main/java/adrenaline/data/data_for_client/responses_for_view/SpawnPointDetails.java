package adrenaline.data.data_for_client.responses_for_view;

import java.io.Serializable;
import java.util.List;

public class SpawnPointDetails extends SquareDetails implements Serializable {
    private String[] weaponsOnSquare;

    public SpawnPointDetails(int id, List<String> players, String[] weapons) {
        super(id, players, true);
        this.weaponsOnSquare = weapons;
    }

    public String[] getWeaponsOnSquare() {
        return this.weaponsOnSquare;
    }
}
