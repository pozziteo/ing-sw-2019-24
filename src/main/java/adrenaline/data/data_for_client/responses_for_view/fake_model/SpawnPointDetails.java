package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains information for spawn point shown to the client
 */

public class SpawnPointDetails extends SquareDetails implements Serializable {
    private WeaponDetails[] weaponsOnSquare;

    public SpawnPointDetails(int id, List<String> players, WeaponDetails[] weapons) {
        super(id, players, true);
        this.weaponsOnSquare = weapons;
    }

    /**
     * Getter method for weapons on square
     * @return weapons on square
     */

    public WeaponDetails[] getWeaponsOnSquare() {
        return this.weaponsOnSquare;
    }
}
