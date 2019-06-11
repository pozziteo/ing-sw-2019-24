package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

public class MapInfo extends DataForClient {
    private String mapPath;

    public MapInfo(String path) {
        this.mapPath = path;
    }

    @Override
    public void updateView(CliUserInterface view) {
        //TODO
    }

    @Override
    public void updateView(GUIController view) {
        //TODO
    }
}
