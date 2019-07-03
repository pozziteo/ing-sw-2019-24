package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;

/**
 * This interface is implemented by every class that has the purpose of notifying the model of any changes
 * during a match
 */

public interface DataForController {
    void updateGame(Controller controller);
}
