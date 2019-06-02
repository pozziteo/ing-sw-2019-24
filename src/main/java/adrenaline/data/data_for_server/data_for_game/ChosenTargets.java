package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

import java.util.List;

public class ChosenTargets extends DataForServer implements DataForController {
    List<String> targets;

    public ChosenTargets(String nickname, List<String> targets) {
        super(nickname);
        this.targets = targets;
    }

    @Override
    public void updateGame(Controller controller) {

    }
}
