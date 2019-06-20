package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.WeaponDetails;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.util.List;

public class ShootOptions extends DataForClient {
    private List<WeaponDetails> playerWeapons;
    private boolean isAdrenaline;
    private List<Integer> validSquares;

    public ShootOptions(boolean isAdrenaline, List<Integer> validSquares, List<WeaponDetails> weapons) {
        this.isAdrenaline = isAdrenaline;
        this.validSquares = validSquares;
        this.playerWeapons = weapons;
    }

    @Override
    public void updateView(CliUserInterface view) {
        if (isAdrenaline)
            view.chooseSquare(validSquares, playerWeapons);
        else
            view.chooseWeapon (playerWeapons);
    }

    @Override
    public void updateView(GUIController view) {
        view.chooseWeapon(playerWeapons);
    }
}
