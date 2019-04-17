package controller;

import model.Game;
import model.ViewUpdater;

public class Controller {
    private Game game;
    private ViewUpdater viewUpdater;

    public Controller(int n) {
        this.game = new Game(n);
        this.viewUpdater = new ViewUpdater (game);
    }

    public void start() {
        this.game.startGame(this.viewUpdater);
    }

    public void endGame() {
        this.game.setEndGame(true);
    }

}
