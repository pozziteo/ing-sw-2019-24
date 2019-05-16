package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.player.Action;

public class ActionToExecute extends DataForServer implements DataForController {
    private Action action;

    public ActionToExecute(String nickname, Action action){
        super(nickname);
        this.action = action;
    }

    @Override
    public void updateGame(Controller controller){
        //controller.executeAction(super.getNickname(), action);
    }
}
