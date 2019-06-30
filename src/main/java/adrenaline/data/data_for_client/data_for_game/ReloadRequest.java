package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.WeaponDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

/**
 * This class is sent to the client to ask if they want to reload any of their unloaded weapons.
 */

public class ReloadRequest extends DataForClient {
    private List<String> ownedAmmo;
    private List<WeaponDetails> unloadedWeapons;

    public ReloadRequest(List<String> ammo, List<WeaponDetails> weapons) {
        this.ownedAmmo = ammo;
        this.unloadedWeapons = weapons;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.askReload(ownedAmmo, unloadedWeapons);
    }
}
