package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.model.deck.Weapon;

public class NewPositionAndGrabbed extends NewPosition {
    private Weapon weapon;

    public NewPositionAndGrabbed(String nickname, int squareId, Weapon w) {
        super(nickname, squareId);
        this.weapon = w;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.executeAction (super.getNickname (), super.getSquareId (), weapon);
    }
}
