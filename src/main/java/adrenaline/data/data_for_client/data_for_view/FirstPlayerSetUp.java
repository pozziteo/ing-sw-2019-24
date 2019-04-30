package adrenaline.data.data_for_client.data_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;
import adrenaline.view.UserInterface;

public class FirstPlayerSetUp extends DataForClient {
    private boolean first;

    public FirstPlayerSetUp(boolean value, Account account) {
        super(account);
        this.first = value;
    }

    public void updateView(UserInterface view) {
        view.setFirstPlayer(first);
    }
}
