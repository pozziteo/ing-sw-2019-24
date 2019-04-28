package adrenaline.data.data_for_server.data_for_game;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.player.Player;

public class FirstPlayerSetUp extends DataForServer {
    private boolean first;

    public FirstPlayerSetUp(boolean value) {
        this.first = value;
    }

    @Override
    public void updateModel() {
        String name = super.getAccount ().getNickName ();
        for (Player p : super.getAccount ().getCurrentLobby ().getGameModel ().getGame ().getPlayers ()) {
            if (name.equals(p.getPlayerName ())) {
                p.setFirstPlayer(first);
            }
        }
    }
}
