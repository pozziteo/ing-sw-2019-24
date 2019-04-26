package view;

import data.DataForClient;
import data.DataForServer;

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
}
