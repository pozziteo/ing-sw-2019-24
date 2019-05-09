package adrenaline.data.data_for_client.data_for_network;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GraphicUserInterface;

public class MessageForClient extends DataForClient {
    private String message;

    public MessageForClient(String message) {
        this.message = message;
    }

    public MessageForClient(Account account, String message) {
        super(account);
        this.message = message;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.getPrinter().print(message);
    }

    @Override
    public void updateView(GraphicUserInterface view) {
        //TODO
    }
}
