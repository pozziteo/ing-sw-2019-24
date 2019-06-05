package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.WeaponDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class ShootOptions extends DataForClient {
    private List<WeaponDetails> playerWeapons;

    public ShootOptions(List<WeaponDetails> weapons) {
        this.playerWeapons = weapons;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.chooseWeapon (playerWeapons);
    }
}
