package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;
import adrenaline.view.cli.CliUserInterface;

public class TimeOutNotice extends DataForClient {

    public TimeOutNotice(Account account) {
        super(account);
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.notifyTimeOut();
    }

}
