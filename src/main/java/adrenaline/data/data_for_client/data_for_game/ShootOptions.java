package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.WeaponDetails;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.util.List;

/**
 * This class is sent to the client to ask which weapon they want to use for the shoot action
 * and, if the action is 'adrenaline', the ids of squares they can move to.
 */

public class ShootOptions extends DataForClient {
    private List<WeaponDetails> playerWeapons;
    private boolean isAdrenaline;
    private boolean isFinalFrenzy;
    private List<Integer> validSquares;

    public ShootOptions(boolean isAdrenaline, boolean isFinalFrenzy, List<Integer> validSquares, List<WeaponDetails> weapons) {
        this.isAdrenaline = isAdrenaline;
        this.isFinalFrenzy = isFinalFrenzy;
        this.validSquares = validSquares;
        this.playerWeapons = weapons;
    }

    @Override
    public void updateView(CliUserInterface view) {
        if (isAdrenaline || isFinalFrenzy)
            view.chooseSquare(validSquares, playerWeapons);
        else
            view.chooseWeapon (playerWeapons);
    }

    @Override
    public void updateView(GUIController view) {
        if (isAdrenaline || isFinalFrenzy)
            view.chooseSquare(validSquares, playerWeapons);
        else
            view.chooseWeapon(playerWeapons);
    }
}
