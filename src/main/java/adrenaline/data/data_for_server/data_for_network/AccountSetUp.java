package adrenaline.data.data_for_server.data_for_network;

import adrenaline.data.data_for_server.DataForServer;

//TODO javadoc

public class AccountSetUp extends DataForServer {
    private String nickname;

    public AccountSetUp(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

    @Override
    public void updateModel() {

    }
}
