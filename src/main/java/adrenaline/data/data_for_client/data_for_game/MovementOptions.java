package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.util.List;

public class MovementOptions extends DataForClient {
    private List<Integer> possiblePaths;

    public MovementOptions(List<Integer> possiblePaths) {
        this.possiblePaths = possiblePaths;
    }

    public List<Integer> getPossiblePaths() {
        return this.possiblePaths;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.showPaths(possiblePaths);
    }

    @Override
    public void updateView(GUIController view) {
        view.showPaths(possiblePaths);
    }
}
