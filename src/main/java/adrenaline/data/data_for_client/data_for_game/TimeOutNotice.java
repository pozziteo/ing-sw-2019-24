package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

/**
 * This class is sent to the client to notify that the timer in Controller has run out.
 */

public class TimeOutNotice extends DataForClient {

    public TimeOutNotice(Account account) {
        super(account);
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.notifyTimeOut(super.getAccount ().getNickName ());
    }

    @Override
    public void updateView(GUIController view) {
        view.notifyTimeOut();
    }

}
