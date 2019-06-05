package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

import java.util.List;

public class ChosenTargets extends DataForServer implements DataForController {
    private List<AtomicTarget> targets;

    public ChosenTargets(String nickname, List<AtomicTarget> targets) {
        super(nickname);
        this.targets = targets;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.setTargets (super.getNickname (), targets);
    }
}
