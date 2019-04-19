package controller;

import model.Model;
import obs.*;
import view.UserInterface;

public class Controller implements Observer {
    private Model model;
    private UserInterface view;

    public Controller(Model model, UserInterface view) {
        this.model = model;
        this.view = view;
        view.attach(this);
    }

    public void update(Object object) {

    }

}
