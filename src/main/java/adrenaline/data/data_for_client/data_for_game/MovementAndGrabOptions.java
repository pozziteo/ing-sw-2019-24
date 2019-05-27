package adrenaline.data.data_for_client.data_for_game;

import adrenaline.model.map.Map;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class MovementAndGrabOptions extends MovementOptions {
    private Map map;

    public MovementAndGrabOptions(List<Integer> possiblePaths, Map map) {
        super(possiblePaths);
        this.map = map;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.showPathsAndGrabOptions (super.getPossiblePaths(), map);
    }
}
