package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.model.deck.Weapon;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class ShootOptions extends DataForClient {
    private List<Weapon> playerWeapons;

    public ShootOptions(List<Weapon> weapons) {
        this.playerWeapons = weapons;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.chooseWeapon (playerWeapons);
    }
}
