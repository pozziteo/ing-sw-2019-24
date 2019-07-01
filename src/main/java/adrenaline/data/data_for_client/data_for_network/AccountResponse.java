package adrenaline.data.data_for_client.data_for_network;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

public class AccountResponse extends DataForClient {
    private boolean successful;
    private String message;

    public AccountResponse(Account account, boolean value, String message) {
        super(account);
        this.successful = value;
        this.message = message;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.getPrinter().print (message);
        if (!successful) {
            view.getPrinter ().print ("Try again...\n");
            view.setUpAccount ();
        } else {
            view.setNickname (super.getAccount ().getNickName ());
        }
    }

    @Override
    public void updateView(GUIController view) {
        view.setNickname(super.getAccount().getNickName());
        view.entryLobby();
        view.showMessage(message);
    }


}
