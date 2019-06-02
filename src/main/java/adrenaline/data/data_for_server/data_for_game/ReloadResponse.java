package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

public class ReloadResponse extends DataForServer implements DataForController {
    private boolean positive;
    private String weaponName;

    public ReloadResponse(String nickname, boolean positive, String weaponName) {
        super(nickname);
        this.positive = positive;
        this.weaponName = weaponName;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.reloadWeapon(super.getNickname (), positive, weaponName);
    }
}
