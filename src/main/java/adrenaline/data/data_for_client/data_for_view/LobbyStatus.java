package adrenaline.data.data_for_client.data_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;
import adrenaline.view.UserInterface;

public class LobbyStatus extends DataForClient {
    private boolean ready;
    private String message;

    public LobbyStatus(Account account, boolean value, String message) {
        super(account);
        this.ready = value;
        this.message = message;
    }

    @Override
    public void updateView(UserInterface view) {
        view.waitLobby (ready, message);
    }
}
