package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class WeaponModeOptions extends DataForClient {
    private List<EffectDetails> effects;

    public WeaponModeOptions(List<EffectDetails> effects) {
        this.effects = effects;
    }

    @Override
    public void updateView(CliUserInterface view) {

    }
}
