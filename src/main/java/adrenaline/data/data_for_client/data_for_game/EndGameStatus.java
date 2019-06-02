package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class EndGameStatus extends DataForClient {
    private List<String> finalRanking;

    public EndGameStatus(List<String> ranking) {
        this.finalRanking = ranking;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.showEndGameScreen(finalRanking);
    }
}
