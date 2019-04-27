package adrenaline.controller;

import adrenaline.data.DataForServer;
import adrenaline.model.GameModel;
import adrenaline.network.Account;

/**
 * Class that manages data received in every single thread (server-side).
 */

public class PlayerController extends Controller {
    private Account account;

    public PlayerController(GameModel gameModel, Account account) {
        super(gameModel);
        this.account = account;
    }

    public void receiveData(DataForServer data) {
        updateGame(data);
    }

    public void updateGame(DataForServer data) {
        //TODO implement
    }
}
