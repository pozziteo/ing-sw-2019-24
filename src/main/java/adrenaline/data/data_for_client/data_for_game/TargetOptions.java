package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.SquareDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class TargetOptions extends DataForClient {
    private List<SquareDetails> map;
    private int maxAmount;

    public TargetOptions(List<SquareDetails> map, int n) {
        this.map = map;
        this.maxAmount = n;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.chooseTargets (maxAmount, map);
    }
}
