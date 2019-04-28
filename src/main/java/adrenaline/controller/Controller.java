package adrenaline.controller;

import adrenaline.data.DataForServer;
import adrenaline.data.data_for_game.MapSetUp;
import adrenaline.model.GameModel;

public class Controller {
    private GameModel gameModel;

    public Controller(GameModel model) {
        this.gameModel = model;
    }

    public GameModel getGameModel() {
        return this.gameModel;
    }

    public void receiveData(DataForServer data) {
        if (data instanceof MapSetUp) {
            updateGame ((MapSetUp) data);
        }
    }

    private void updateGame(MapSetUp data) {
        gameModel.setMap(data.getFilename ());
    }
}
