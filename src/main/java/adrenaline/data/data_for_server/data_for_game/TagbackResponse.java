package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

public class TagbackResponse extends DataForServer implements DataForController {
    private String target;
    private boolean toBeUsed;

    public TagbackResponse(String nickname, String target, boolean response) {
        super(nickname);
        this.target = target;
        this.toBeUsed = response;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.useTagback (toBeUsed, super.getNickname (), target);
    }
}
