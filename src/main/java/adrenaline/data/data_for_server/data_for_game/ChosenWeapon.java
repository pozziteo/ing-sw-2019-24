package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.deck.Weapon;


public class ChosenWeapon extends DataForServer implements DataForController {
    private Weapon weapon;

    public ChosenWeapon(String nickname, Weapon weapon) {
        super(nickname);
        this.weapon = weapon;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.sendPossibleTargets(super.getNickname (), weapon);
    }
}
