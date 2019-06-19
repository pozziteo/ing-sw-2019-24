package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.util.Map;

public class MapSetUp extends DataForClient {
    private String chooserPlayer;
    private Map<String, String> playerColors;

    public MapSetUp(String chooser, Map<String, String> colors) {
        this.chooserPlayer = chooser;
        this.playerColors = colors;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.selectMap(chooserPlayer, playerColors);
    }

    @Override
    public void updateView(GUIController view) {
        view.selectMap(chooserPlayer, playerColors);
    }
}
