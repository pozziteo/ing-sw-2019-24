package adrenaline.data.data_for_server.data_for_game;

import adrenaline.data.data_for_server.DataForServer;

public class MapSetUp extends DataForServer {
    private String filename;

    public MapSetUp(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return this.filename;
    }

    @Override
    public void updateModel() {
        super.getAccount().getCurrentLobby ().getGameModel ().getGame ().setArena (filename);
    }
}
