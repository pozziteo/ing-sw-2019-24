package data;

//TODO javadoc

import controller.Controller;

import java.io.Serializable;

public abstract class DataForServer implements Serializable {
    private String nickname;
    private Controller controller;

    public DataForServer() {
    }

    /**
     * Method to forward the data to the controller
     */

    public void sendToController() {
        this.controller.receiveData (this);
    }
}
