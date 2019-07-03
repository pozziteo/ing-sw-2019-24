package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

import java.util.List;

/**
 * This class is used to inform the model about which atomic targets have been chosen for Shoot action
 */

public class ChosenTargets extends DataForServer implements DataForController {
    private List<AtomicTarget> targets;
    private String targetingScopeTarget;

    public ChosenTargets(String nickname, List<AtomicTarget> targets, String targetingScopeTarget) {
        super(nickname);
        this.targets = targets;
        this.targetingScopeTarget = targetingScopeTarget;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.setTargets (super.getNickname (), targets, targetingScopeTarget);
    }
}
