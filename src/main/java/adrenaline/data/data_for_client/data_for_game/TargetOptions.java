package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.SquareDetails;
import adrenaline.data.data_for_client.responses_for_view.fake_model.TargetDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class TargetOptions extends DataForClient {
    private List<TargetDetails> targets;
    private List<SquareDetails> map;

    public TargetOptions(List<TargetDetails> targets, List<SquareDetails> map) {
        this.targets = targets;
        this.map = map;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.chooseTargets (targets, map);
    }

}
