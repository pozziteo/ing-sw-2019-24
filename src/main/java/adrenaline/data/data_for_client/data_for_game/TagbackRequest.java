package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;

public class TagbackRequest extends DataForClient {

    @Override
    public void updateView(CliUserInterface view) {
        view.askTagback();
    }
}