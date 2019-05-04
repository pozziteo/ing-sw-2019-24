package adrenaline.network;

import adrenaline.data.data_for_server.DataForServer;

/**
 * Interface that represents a generic client.
 */

public interface ClientInterface {

    /**
     * Method to connect a generic client to the server of its corresponding type.
     */
    void connectToServer();

    /**
     * Method to send data from view to controller
     * @param data to send
     */
    void sendData(DataForServer data);

}
