package adrenaline.data.data_for_client.data_for_network;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GraphicUserInterface;

public class ClientSetUp extends DataForClient {
    public ClientSetUp(Account account) {
        super(account);
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.setNickname(super.getAccount ().getNickName ());
    }

    @Override
    public void updateView(GraphicUserInterface view) {
        //TODO
    }
}
