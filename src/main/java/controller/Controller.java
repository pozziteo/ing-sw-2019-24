package controller;

import data.DataForServer;
import data.data_for_network.AccountSetUp;
import model.GameModel;
import network.visitors.Account;
import view.UserInterface;

//TODO javadoc

public class Controller {
    private GameModel gameModel;
    private UserInterface view;

    public Controller(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void receiveData(DataForServer data) {
        updateGame(data);
    }

    public void updateGame(DataForServer data) {
        //TODO implement
    }

    public void receiveData(AccountSetUp data) {
        //addPlayer (data.g);
    }

    public void addPlayer(Account account) {
        this.gameModel.setPlayers (account);
    }
}
