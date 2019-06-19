package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

public class TimeOutNotice extends DataForClient {

    public TimeOutNotice(Account account) {
        super(account);
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.notifyTimeOut();
    }

    @Override
    public void updateView(GUIController view) {
        view.notifyTimeOut();
    }

}
