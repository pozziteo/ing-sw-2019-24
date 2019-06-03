package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.SquareDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class TargetOptions extends DataForClient {
    private EffectDetails effect;
    private List<SquareDetails> map;

    public TargetOptions(EffectDetails effect, List<SquareDetails> map) {
        this.effect = effect;
        this.map = map;
    }

    @Override
    public void updateView(CliUserInterface view) {
        if (effect.getAreaType ().equals("") && effect.getTargetType ().equals("single")) {
            view.chooseSingleTarget (map);
        } else if (effect.getAreaType ().equals("") && effect.getTargetType ().equals("multiple")) {
            view.chooseMultipleTargets (effect.getTargetsAmount (), map);
        } else {
            view.chooseAreaToTarget(effect.getAreaType (), map);
        }
    }

}
