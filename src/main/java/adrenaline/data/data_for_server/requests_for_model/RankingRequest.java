package adrenaline.data.data_for_server.requests_for_model;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.data.data_for_server.data_for_game.DataForController;

public class RankingRequest extends DataForServer implements DataForController {

    public RankingRequest(String nickname) {
        super(nickname);
    }

    @Override
    public void updateGame(Controller controller) {
        controller.getGameModel ().updateRanking (controller.getLobby().findPlayer (super.getNickname ()));
    }
}