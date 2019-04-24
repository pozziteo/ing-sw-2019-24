package controller;

import model.Model;
import view.UserInterface;

public class Controller {
    private Model model;
    private UserInterface view;

    public Controller(Model model, UserInterface view) {
        this.model = model;
        this.view = view;
    }

}
