package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;
import java.util.List;

public class SpawnPointDetails extends SquareDetails implements Serializable {
    private WeaponDetails[] weaponsOnSquare;

    public SpawnPointDetails(int id, List<String> players, WeaponDetails[] weapons) {
        super(id, players, true);
        this.weaponsOnSquare = weapons;
    }

    public WeaponDetails[] getWeaponsOnSquare() {
        return this.weaponsOnSquare;
    }
}
