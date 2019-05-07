package adrenaline.data.data_for_server.data_for_game;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.MainServer;

public class MapSetUp extends DataForServer {
    private String filename;

    public MapSetUp(String nickname, String filename) {
        super(nickname);
        this.filename = filename;
    }

    @Override
    public void updateServer(MainServer server) {
        server.findClient(super.getNickname ()).getCurrentLobby ().getGameModel ().getGame ().setArena (filename);
    }
}
