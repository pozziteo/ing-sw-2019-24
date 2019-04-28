package adrenaline.data.data_for_network;

import adrenaline.data.DataForServer;

//TODO javadoc

public class AccountSetUp implements DataForServer {
    private String nickname;

    public AccountSetUp(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }
}
