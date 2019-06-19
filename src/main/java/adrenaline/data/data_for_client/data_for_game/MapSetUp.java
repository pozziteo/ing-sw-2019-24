package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

public class MapSetUp extends DataForClient {
    private String chooserPlayer;
    private String playerColor;

    public MapSetUp(String chooser, String color) {
        this.chooserPlayer = chooser;
        this.playerColor = color;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.selectMap(chooserPlayer, playerColor);
    }

    @Override
    public void updateView(GUIController view) {
        view.selectMap(chooserPlayer, playerColor);
    }
}
