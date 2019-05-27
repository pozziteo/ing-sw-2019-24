package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;

public class Turn extends DataForClient {
    private String currentPlayer;

    public Turn(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.showTurn(currentPlayer);
    }
}
