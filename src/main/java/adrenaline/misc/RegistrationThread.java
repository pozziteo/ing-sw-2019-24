package adrenaline.misc;

import adrenaline.data.data_for_client.data_for_view.AccountResponse;

public class RegistrationThread extends Thread {
    private final AccountResponse response;

    public RegistrationThread(AccountResponse response) {
        this.response = response;
    }

    @Override
    public void run() {
        response.sendToView ();
    }

}
