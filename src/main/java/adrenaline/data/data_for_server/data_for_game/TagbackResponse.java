package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

/**
 * This class is used to inform the model about whether or not the client wants to use Tagback Grenade
 */

public class TagbackResponse extends DataForServer implements DataForController {
    private String target;
    private boolean toBeUsed; //true if client wants to use it

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
