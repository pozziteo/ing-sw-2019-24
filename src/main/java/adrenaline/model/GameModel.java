package adrenaline.model;

import adrenaline.data.data_for_client.responses_for_view.MapResponse;
import adrenaline.data.data_for_client.responses_for_view.RankingResponse;
import adrenaline.data.data_for_client.responses_for_view.SquareDetailsResponse;
import adrenaline.model.player.Player;
import adrenaline.network.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//TODO javadoc

public class GameModel implements Serializable {
    private static final long serialVersionUID = 5055517717183645074L;

    private Game game;

    public GameModel(String[] playerNames) {
        this.game = new Game(playerNames);
    }

    public Game getGame() {
        return this.game;
    }

    public void updateMap(Account account) {
        MapResponse response = new MapResponse (game.getMap ());
        response.setAccount (account);
        response.sendToView ();
    }

    public void updateSquareDetails(Account account) {
        SquareDetailsResponse response = new SquareDetailsResponse (game.getMap ());
        response.setAccount (account);
        response.sendToView ();
    }

    public void updateRanking(Account account) {
        List<String> ranking = new ArrayList<> ();
        for (Player p : game.getRanking ())
            ranking.add(p.getPlayerName ());
        RankingResponse response = new RankingResponse (ranking);
        response.setAccount (account);
        response.sendToView ();
    }

    public void updateBoards(Account account) {
        //TODO
    }

    public void updatePlayerBoard(Account account) {
        //TODO
    }

}
