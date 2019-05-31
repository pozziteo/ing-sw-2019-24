package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;


public class ChosenWeapon extends DataForServer implements DataForController {
    private String weapon;

    public ChosenWeapon(String nickname, String weapon) {
        super(nickname);
        this.weapon = weapon;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.sendPossibleTargets(super.getNickname (), weapon);
    }
}
