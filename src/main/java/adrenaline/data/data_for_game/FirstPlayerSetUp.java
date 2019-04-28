package adrenaline.data.data_for_game;

import adrenaline.data.DataForServer;

public class FirstPlayerSetUp implements DataForServer {
    private boolean first;

    public FirstPlayerSetUp(boolean value) {
        this.first = value;
    }
}
