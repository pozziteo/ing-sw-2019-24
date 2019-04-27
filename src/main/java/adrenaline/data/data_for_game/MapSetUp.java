package adrenaline.data.data_for_game;

import adrenaline.data.DataForServer;

public class MapSetUp extends DataForServer {
    private String filename;

    public MapSetUp(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return this.filename;
    }
}
