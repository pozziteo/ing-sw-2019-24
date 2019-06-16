package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.PowerUpDetails;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.util.List;

public class SpawnPointSetUp extends DataForClient {
    private List<PowerUpDetails> powerUps;

    public SpawnPointSetUp(List<PowerUpDetails> powerUps) {
        this.powerUps = powerUps;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.chooseSpawnPoint (powerUps);
    }

    @Override
    public void updateView(GUIController view) {
        view.chooseSpawnPoint(powerUps);
    }
}