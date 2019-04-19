package view;

import obs.*;

/**
 * Interface that extends both the Observer and Observable ends of the pattern.
 * Implemented by two different classes in the view package based on the
 * user interface type chosen by the client (cli or gui).
 */

public interface UserInterface extends Observer, Observable {

    //Method for observer

    void update(Object object);

    //Methods for observable

    void attach(Observer observer);

    void detach(Observer observer);

    void inform();
}
