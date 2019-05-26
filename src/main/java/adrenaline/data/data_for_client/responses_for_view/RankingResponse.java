package adrenaline.data.data_for_client.responses_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class RankingResponse extends DataForClient {
    private List<String> ranking;

    public RankingResponse(List<String> ranking) {
        this.ranking = ranking;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.printRanking (ranking);
    }
}
