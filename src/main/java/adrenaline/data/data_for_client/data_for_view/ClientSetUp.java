package adrenaline.data.data_for_client.data_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;
import adrenaline.view.UserInterface;

public class ClientSetUp extends DataForClient {

    public ClientSetUp(Account account) {
        super(account);
    }
    public void updateView(UserInterface view) {
        view.setNickname(super.getAccount ().getNickName ());
    }
}
