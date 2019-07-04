package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.WeaponDetails;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.util.List;

/**
 * This class is sent to the client to ask if they want to reload any of their unloaded weapons.
 */

public class ReloadRequest extends DataForClient {
    private List<String> ownedAmmo;
    private List<WeaponDetails> unloadedWeapons;
    private boolean isBeforeShoot;

    public ReloadRequest(List<String> ammo, List<WeaponDetails> weapons, boolean isBeforeShoot) {
        this.ownedAmmo = ammo;
        this.unloadedWeapons = weapons;
        this.isBeforeShoot = isBeforeShoot;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.askReload(ownedAmmo, unloadedWeapons, isBeforeShoot);
    }

    @Override
    public void updateView(GUIController view) {
        view.askReload(unloadedWeapons, isBeforeShoot);
    }
}
