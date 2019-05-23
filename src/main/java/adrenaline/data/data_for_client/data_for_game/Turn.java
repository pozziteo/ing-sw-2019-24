package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.model.map.Map;
import adrenaline.view.cli.CliUserInterface;

public class Turn extends DataForClient {
    private String currentPlayer;
    private Map map;

    public Turn(String currentPlayer, Map map) {
        this.currentPlayer = currentPlayer;
        this.map = map;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.showTurn(currentPlayer, map);
    }
}
