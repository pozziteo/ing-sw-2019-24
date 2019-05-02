package adrenaline.data.data_for_client;

import adrenaline.network.Account;
import adrenaline.view.UserInterface;

import java.io.Serializable;

/**
 * Class that represents generic data that has to be sent to the client.
 */

public abstract class DataForClient implements Serializable {
    private Account account;

    public DataForClient(Account account) {
        this.account = account;
    }

    /**
     * Method to send data to the client's user interface
     */
    public void sendToView() {
        if (account.isOnline()) {
            try {
                account.sendData(this);
            } catch (Exception e) {
                System.err.println (e.getMessage());
            }
        } else {
            System.err.println(account.getNickName() + " is offline.");
        }
    }

    /**
     * Method that implements command pattern
     * @param view to update
     */
    public void updateView(UserInterface view) {
        //implemented by subclasses
    }
}
