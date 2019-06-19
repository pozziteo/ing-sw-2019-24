package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.SquareDetails;
import adrenaline.data.data_for_client.responses_for_view.fake_model.TargetDetails;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.util.List;

public class TargetOptions extends DataForClient {
    private List<TargetDetails> targets;
    private List<String> compliantTargets;
    private List<SquareDetails> map;
    private boolean hasTargetingScope;

    public TargetOptions(List<TargetDetails> targets, List<String> compliantTargets, List<SquareDetails> map, boolean value) {
        this.targets = targets;
        this.compliantTargets = compliantTargets;
        this.map = map;
        this.hasTargetingScope = value;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.chooseTargets (targets, compliantTargets, map, hasTargetingScope);
    }

//    @Override
//    public void updateView(GUIController view) {
//        view.chooseTargets(targets, compliantTargets, map, hasTargetingScope);
//    }

}
