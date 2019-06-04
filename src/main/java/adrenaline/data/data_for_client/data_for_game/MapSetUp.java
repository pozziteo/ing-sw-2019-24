package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

public class MapSetUp extends DataForClient {

    private String chooserPlayer;

    public MapSetUp(String chooser) {
        this.chooserPlayer = chooser;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.selectMap(chooserPlayer);
    }

    @Override
    public void updateView(GUIController view) {
        view.selectMap(chooserPlayer);
    }
}
