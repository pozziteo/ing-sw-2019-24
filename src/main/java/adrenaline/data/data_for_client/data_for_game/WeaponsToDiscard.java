package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.WeaponDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

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
