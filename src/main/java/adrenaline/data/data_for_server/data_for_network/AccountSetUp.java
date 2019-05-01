package adrenaline.data.data_for_server.data_for_network;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.Account;
import adrenaline.network.MainServer;

//TODO javadoc

public class AccountSetUp extends DataForServer {

    public AccountSetUp(String nickname) {
        super(nickname);
    }

    @Override
    public void updateServer(MainServer server) {
        server.registerAccount (new Account(super.getNickname(), server));
    }
}
