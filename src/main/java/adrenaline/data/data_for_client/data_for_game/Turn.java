package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

/**
 * This class is sent to the client to ask which action they want to perform in their turn.
 */

public class Turn extends DataForClient {
    private String currentPlayer;
    private boolean finalFrenzy;
    private boolean beforeFirstPlayer;

    public Turn(String currentPlayer, boolean finalFrenzy, boolean beforeFirstPlayer) {
        this.currentPlayer = currentPlayer;
        this.finalFrenzy = finalFrenzy;
        this.beforeFirstPlayer = beforeFirstPlayer;
    }

    @Override
    public void updateView(CliUserInterface view) {
        if (!finalFrenzy)
            view.showTurn(currentPlayer);
        else
            view.showFinalFrenzyTurn (currentPlayer, beforeFirstPlayer);
    }

    @Override
    public void updateView(GUIController view) {
        if (!finalFrenzy)
            view.showTurn(currentPlayer);
        else
            view.showFinalFrenzyTurn(currentPlayer, beforeFirstPlayer);
    }
}
