package data.data_for_network;

import data.DataForServer;

//TODO javadoc

public class AccountSetUp extends DataForServer {
    private String nickname;

    public AccountSetUp(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

}
