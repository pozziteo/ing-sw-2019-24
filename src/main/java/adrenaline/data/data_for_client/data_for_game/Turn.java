package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.model.map.Map;
import adrenaline.view.cli.CliUserInterface;

public class Turn extends DataForClient {
    private boolean isPlaying;
    private Map map;

    public Turn(boolean isPlaying, Map map) {
        this.isPlaying = isPlaying;
        this.map = map;
    }

    @Override
    public void updateView(CliUserInterface view) {
        if (isPlaying) {
            view.selectAction (map);
        } else {
            //TODO
        }
    }
}
