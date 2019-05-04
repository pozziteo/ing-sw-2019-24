package adrenaline.view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.map.Square;

/**
 * Interface implemented by two different classes in the view package based on the
 * user interface type chosen by the client (cli or gui).
 */

public interface UserInterface {

    /**
     * Method that receives data from the model in order to update the client's user interface.
     * @param data that has to be updated
     */
    void updateView(DataForClient data);

    /**
     * Method that takes data created by a user's action and sends it to the controller.
     * @param data to send
     */
    void sendToController(DataForServer data);

    /**
     * Method to set up a client's account
     */
    void setUpAccount();

    /**
     * Method to choose the player that sets up the match
     * @param value true (if first), false (otherwise)
     */
    void setFirstPlayer(boolean value);

    void loginStatus(boolean value, String message);

    void printMap(Square[] arena);

    void setNickname(String nickname);
}
