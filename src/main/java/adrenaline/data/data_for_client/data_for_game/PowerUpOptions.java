package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.PowerUpDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class PowerUpOptions extends DataForClient {
    private List<PowerUpDetails> powerUps;

    public PowerUpOptions(List<PowerUpDetails> powerUpDetails) {
        this.powerUps = powerUpDetails;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.choosePowerUp(powerUps);
    }
}
