package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.EffectDetails;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.util.List;

/**
 * This class is sent to the client to ask which effect they want to perform with the chosen weapon
 * in the shoot action.
 */

public class WeaponModeOptions extends DataForClient {
    private List<EffectDetails> effects;

    public WeaponModeOptions(List<EffectDetails> effects) {
        this.effects = effects;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.chooseWeaponEffect(effects);
    }

    @Override
    public void updateView(GUIController view) {
        view.chooseWeaponEffect(effects);
    }
}
