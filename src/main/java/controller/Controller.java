package controller;

import model.GameModel;
import network.visitors.Account;
import view.UserInterface;

public class Controller {
    private GameModel gameModel;
    private UserInterface view;

    public Controller(GameModel gameModel, UserInterface view) {
        this.gameModel = gameModel;
        this.view = view;
    }

    public void addPlayer(Account account) {
        this.gameModel.setPlayers (account);
    }

}
