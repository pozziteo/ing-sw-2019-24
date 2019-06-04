package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

import java.util.ArrayList;
import java.util.List;

public class ChosenTargets extends DataForServer implements DataForController {
    private List<String> targets;
    private List<Integer> squareId;
    private String areaType;

    public ChosenTargets(String nickname, List<String> targets) {
        super(nickname);
        this.targets = targets;
        this.squareId = new ArrayList<> ();
        this.areaType = "";
    }

    public ChosenTargets(String nickname, List<Integer> squareId, String type) {
        super(nickname);
        this.targets = null;
        this.squareId = squareId;
        this.areaType = type;
    }

    @Override
    public void updateGame(Controller controller) {
        if (targets == null) {
            if (areaType.equals("room"))
                controller.setRoomBasedTargets(super.getNickname (), squareId);
            else
                controller.setSquareBasedTargets(super.getNickname (), squareId);
        } else
            controller.setTargets (super.getNickname (), targets);
    }
}
