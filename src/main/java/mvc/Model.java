package mvc;

import model.Game;

import java.util.ArrayList;
import java.util.List;

public class Model implements Observable {
    private Game game;
    private List<Observer> observers = new ArrayList<> ();

    public Model(Game game) {
        this.game = game;
    }

    public void attach(Observer observer) {
        this.observers.add (observer);
    }

    public void detach(Observer observer) {
        this.observers.remove (observer);
    }

    public void inform() {

    }


}
