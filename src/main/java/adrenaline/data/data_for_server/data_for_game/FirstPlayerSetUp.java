package adrenaline.data.data_for_server.data_for_game;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.player.Player;
import adrenaline.network.MainServer;

import java.util.List;

public class FirstPlayerSetUp extends DataForServer {
    private boolean first;

    public FirstPlayerSetUp(String nickname, boolean value) {
        super(nickname);
        this.first = value;
    }


    @Override
    public void updateServer(MainServer server) {
        List<Player> players = super.findAccount (server.getStoredAccounts ()).getCurrentLobby ().getGameModel ().getGame ().getPlayers ();
        for (Player p : players) {
            if (super.getNickname ().equals(p.getPlayerName ())) {
                p.setFirstPlayer(first);
                break;
            }
        }
    }
}
