package adrenaline.data.data_for_client.responses_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;

/**
 * This class is used to send map information to the client
 */

public class MapResponse extends DataForClient {
    private String mapName;

    public MapResponse(String mapName) {
        this.mapName = mapName;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.printMap (mapName);
    }
}
