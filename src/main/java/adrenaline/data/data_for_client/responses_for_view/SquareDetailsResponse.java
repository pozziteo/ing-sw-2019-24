package adrenaline.data.data_for_client.responses_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.SquareDetails;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.util.List;

public class SquareDetailsResponse extends DataForClient {
    private List<SquareDetails> map;

    public SquareDetailsResponse(List<SquareDetails> map) {
        this.map = map;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.printSquareDetails (map);
    }

    @Override
    public void updateView(GUIController view) {
        view.updateMap(map);
    }
}
