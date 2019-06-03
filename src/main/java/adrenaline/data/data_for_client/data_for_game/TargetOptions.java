package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;

public class TargetOptions extends DataForClient {
    private EffectDetails effect;

    public TargetOptions(EffectDetails effect) {
        this.effect = effect;
    }

    @Override
    public void updateView(CliUserInterface view) {
        if (effect.getAreaType ().equals("")) {

        } else {
            view.chooseAreaToTarget(effect.getAreaType ());
        }
    }

}
