package adrenaline.data.data_for_client.data_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;
import adrenaline.view.UserInterface;

public class TimeOutExpired extends DataForClient {
    String message;

    public TimeOutExpired(Account account, String message) {
        super(account);
        this.message = message;
    }

    @Override
    public void updateView(UserInterface view) {
        view.showTimeOutExpired(message);
    }
}
