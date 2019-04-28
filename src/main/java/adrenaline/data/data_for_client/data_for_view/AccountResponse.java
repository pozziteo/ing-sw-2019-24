package adrenaline.data.data_for_client.data_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.network.Account;

public class AccountResponse extends DataForClient {
    private boolean successful;
    private String message;

    public AccountResponse(Account account, boolean value, String message) {
        super(account);
        this.successful = value;
        this.message = message;
    }

    public boolean isSuccessful() {
        return this.successful;
    }

    public String getMessage() {
        return this.message;
    }
}
