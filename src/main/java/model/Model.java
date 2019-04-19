package model;

import obs.*;

import java.util.ArrayList;
import java.util.List;

public class Model implements Observable {
    private Game game;
    private List<Observer> observers;

    public Model(Game game) {
        this.game = game;
        this.observers = new ArrayList<> ();
    }

    public void attach(Observer observer) {
        this.observers.add (observer);
    }

    public void detach(Observer observer) {
        this.observers.remove (observer);
    }

    public void inform() {

    }

    public Game getGame() {
        return this.game;
    }

}
