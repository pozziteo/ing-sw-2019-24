package adrenaline.data.data_for_client.data_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;
import adrenaline.view.UserInterface;

public class MessageForClient extends DataForClient {
    private String message;

    public MessageForClient(Account account, String message) {
        super(account);
        this.message = message;
    }

    @Override
    public void updateView(UserInterface view) {
        view.showMessageFromServer(message);
    }
}
