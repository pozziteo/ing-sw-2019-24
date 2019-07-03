package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

/**
 * This class is used to inform the model about which action type has been chosen for the current turn action
 */

public class ActionBuilder extends DataForServer implements DataForController {
    private String actionType;

    public ActionBuilder(String nickname, String actionType){
        super(nickname);
        this.actionType = actionType;
    }

    @Override
    public void updateGame(Controller controller){
        controller.buildAction(actionType, super.getNickname());
    }
}
