package adrenaline.data.data_for_server.data_for_game;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.GameModel;
import adrenaline.model.player.Player;

import java.util.List;

public class FirstPlayerSetUp extends DataForServer implements DataForController {
    private boolean first;

    public FirstPlayerSetUp(String nickname, boolean value) {
        super(nickname);
        this.first = value;
    }

    @Override
    public void updateGame(GameModel game) {
        List<Player> players = game.getGame ().getPlayers ();
        for (Player p : players) {
            if (super.getNickname ().equals(p.getPlayerName ())) {
                p.setFirstPlayer(first);
                break;
            }
        }
    }
}
