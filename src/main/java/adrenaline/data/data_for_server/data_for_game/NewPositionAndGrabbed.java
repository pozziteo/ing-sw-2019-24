package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;

public class NewPositionAndGrabbed extends NewPosition {
    private String weapon;

    public NewPositionAndGrabbed(String nickname, int squareId, String weapon) {
        super(nickname, squareId);
        this.weapon = weapon;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.executeAction (super.getNickname (), super.getSquareId (), weapon);
    }
}
