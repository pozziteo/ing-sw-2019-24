package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

public class Turn extends DataForClient {
    private String currentPlayer;

    public Turn(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.showTurn(currentPlayer);
    }

    @Override
    public void updateView(GUIController view) {
        view.showTurn(currentPlayer);
    }
}
