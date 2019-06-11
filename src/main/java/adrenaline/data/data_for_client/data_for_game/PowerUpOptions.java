package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.PowerUpDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.ArrayList;
import java.util.List;

//TODO
public class PowerUpOptions extends DataForClient {
    private List<PowerUpDetails> powerUps;

    public PowerUpOptions(List<PowerUpDetails> powerUpDetails) {
        this.powerUps = new ArrayList<>();
        for (PowerUpDetails p : powerUpDetails) {
            if (p.getType().equals("Newton") || p.getType().equals("Teleporter")) {
                powerUps.add(p);
            }
        }
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.choosePowerUp(powerUps);
    }
}
