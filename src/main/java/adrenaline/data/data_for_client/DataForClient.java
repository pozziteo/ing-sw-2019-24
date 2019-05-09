package adrenaline.data.data_for_client;

import adrenaline.network.Account;
import adrenaline.view.UserInterface;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GraphicUserInterface;

import java.io.Serializable;

/**
 * Class that represents generic data that has to be sent to the client.
 */

public abstract class DataForClient implements Serializable {
    private static final long serialVersionUID = -6366149212487254142L;
    private Account account;

    public DataForClient() {

    }

    public DataForClient(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
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
    public void updateView(CliUserInterface view) {
        //implemented by subclasses
    }

    public void updateView(GraphicUserInterface view) {
        //implemented by subclasses
    }
}
