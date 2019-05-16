package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class InitialSpawnPointSetUp extends DataForClient {
    private List<PowerUp> powerUps;

    public InitialSpawnPointSetUp(List<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.chooseSpawnPoint (powerUps);
    }
}
