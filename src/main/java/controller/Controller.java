package controller;

import data.DataForServer;
import model.GameModel;

/**
 * Class that manages data received in every single thread (server-side).
 */

public class Controller {
    private GameModel gameModel;

    public Controller(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void receiveData(DataForServer data) {
        updateGame(data);
    }

    public void updateGame(DataForServer data) {
        //TODO implement
    }

    /* public void addPlayer(Account account) {
        this.gameModel.setPlayers (account);
    } */
}
