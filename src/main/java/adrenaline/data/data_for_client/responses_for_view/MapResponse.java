package adrenaline.data.data_for_client.responses_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.model.map.Map;
import adrenaline.view.cli.CliUserInterface;

public class MapResponse extends DataForClient {
    private Map map;

    public MapResponse(Map map) {
        this.map = map;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.printMap (map);
    }
}
