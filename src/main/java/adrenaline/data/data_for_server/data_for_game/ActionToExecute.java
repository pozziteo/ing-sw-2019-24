package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.player.Action;

public class ActionToExecute extends DataForServer implements DataForController {
    private String actionType;

    public ActionToExecute(String nickname, String actionType){
        super(nickname);
        this.actionType = actionType;
    }

    @Override
    public void updateGame(Controller controller){
        controller.buildAction(actionType, super.getNickname());
    }
}
