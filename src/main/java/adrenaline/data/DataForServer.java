package adrenaline.data;

//TODO javadoc

import adrenaline.controller.PlayerController;

import java.io.Serializable;

public abstract class DataForServer implements Serializable {
    private String nickname;
    private PlayerController controller;

    public DataForServer() {
    }

    /**
     * Method to forward the data to the controller
     */

    public void sendToController() {
        this.controller.receiveData (this);
    }
}
