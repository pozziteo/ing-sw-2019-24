package adrenaline.data.data_for_client.responses_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.model.map.Map;
import adrenaline.view.cli.CliUserInterface;

public class SquareDetailsResponse extends DataForClient {
    private Map map;

    public SquareDetailsResponse(Map map) {
        this.map = map;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.printSquareDetails (map);
    }
}
