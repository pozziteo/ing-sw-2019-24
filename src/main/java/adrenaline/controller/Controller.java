package adrenaline.controller;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.GameModel;
import adrenaline.network.MainServer;

public class Controller {
    private MainServer server;
    private GameModel gameModel;

    public Controller(MainServer server, GameModel model) {
        this.server = server;
        this.gameModel = model;
    }

    public GameModel getGameModel() {
        return this.gameModel;
    }

    public void receiveData(DataForServer data) {
        data.updateServer (server);
    }
}
