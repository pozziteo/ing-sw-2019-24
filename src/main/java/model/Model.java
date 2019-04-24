package model;

import controller.Controller;
import data.DataForServer;
import view.UserInterface;

public class Model {
    private Game game;
    private UserInterface view;
    private Controller controller;

    public Model(Game game, UserInterface view, Controller controller) {
        this.game = game;
        this.view = view;
        this.controller = controller;
    }



    public Game getGame() {
        return this.game;
    }

}
