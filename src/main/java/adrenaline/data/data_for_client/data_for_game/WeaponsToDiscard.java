package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.WeaponDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

/**
 * This class is sent to the client to ask which weapon they want to discard after
 * exceeding the maximum number of three.
 */

public class WeaponsToDiscard extends DataForClient {
    private List<WeaponDetails> weapons;

    public WeaponsToDiscard(List<WeaponDetails> weapons) {
        this.weapons = weapons;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.discardWeapon(weapons);
    }
}
