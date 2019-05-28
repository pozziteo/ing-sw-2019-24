package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class TargetOptions extends DataForClient {
    private List<String> possibleTargets;
    private int maxAmount;

    public TargetOptions(List<String> targets, int n) {
        this.possibleTargets = targets;
        this.maxAmount = n;
    }

    @Override
    public void updateView(CliUserInterface view) {
        //TODO
    }
}
