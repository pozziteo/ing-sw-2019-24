package adrenaline.controller;

import adrenaline.data.data_for_server.DataForServer;
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
        data.updateModel ();
    }
}
