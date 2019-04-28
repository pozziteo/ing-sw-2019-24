package adrenaline.network;

import adrenaline.controller.Controller;
import adrenaline.model.GameModel;

import java.util.ArrayList;

public class Lobby {
    private ArrayList<Account> players;
    private GameModel game;
    private Controller controller;
    private boolean full;

    public Lobby() {
        this.players = new ArrayList<> ();
        this.game = new GameModel(players.size ());
        this.controller = new Controller(game);
        this.full = false;
    }

    public Controller getController() {
        return this.controller;
    }

    private boolean checkFull() {
        if (this.players.size () == 5)
            full = true;
        return full;
    }

    public boolean isFull() {
        return checkFull ();
    }

    public void setPlayers(Account a) {
        this.players.add (a);
    }

}
