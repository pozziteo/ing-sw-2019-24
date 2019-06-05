package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

import java.util.List;

public class ChosenTargets extends DataForServer implements DataForController {
    private List<String> targets;
    private List<Integer> squareId;

    public ChosenTargets(String nickname, List<String> targets, List<Integer> squareId) {
        super(nickname);
        this.targets = targets;
        this.squareId = squareId;
    }

    @Override
    public void updateGame(Controller controller) {
        if (targets == null) {
            controller.setAreaBasedTargets (super.getNickname (), squareId);
        } else
            controller.setTargets (super.getNickname (), targets);
    }
}
