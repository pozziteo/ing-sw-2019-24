package adrenaline.data.data_for_server.data_for_network;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.MainServer;

public class AccountSetUp extends DataForServer {
    private String customNickname;

    public AccountSetUp(String defaultNickname, String customNickname) {
        super(defaultNickname);
        this.customNickname = customNickname;
    }

    @Override
    public void updateServer(MainServer server) {
        server.registerAccount (super.getNickname (), customNickname);
    }
}
