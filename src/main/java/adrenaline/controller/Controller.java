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

    //METHODS TO UPDATE MAP INFO IN MODEL

    public void receiveData(DataForServer data) {
        updateGame((MapSetUp) data);
    }

    private void updateGame(MapSetUp data) {
        gameModel.setMap(data.getFilename ());
    }
}
