package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.PowerUpDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class InitialSpawnPointSetUp extends DataForClient {
    private List<PowerUpDetails> powerUps;

    public InitialSpawnPointSetUp(List<PowerUpDetails> powerUps) {
        this.powerUps = powerUps;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.chooseSpawnPoint (powerUps);
    }
}
