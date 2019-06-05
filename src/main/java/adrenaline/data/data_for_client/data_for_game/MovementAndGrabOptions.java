package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.responses_for_view.fake_model.SquareDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class MovementAndGrabOptions extends MovementOptions {
    private List<SquareDetails> map;

    public MovementAndGrabOptions(List<Integer> possiblePaths, List<SquareDetails> map) {
        super(possiblePaths);
        this.map = map;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.showPathsAndGrabOptions (super.getPossiblePaths(), map);
    }
}
