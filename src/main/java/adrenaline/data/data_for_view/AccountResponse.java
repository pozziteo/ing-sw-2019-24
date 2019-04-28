package adrenaline.data.data_for_view;

public class AccountResponse {
    private boolean successful;
    private String message;

    public AccountResponse(boolean value, String message) {
        this.successful = value;
        this.message = message;
    }

    public boolean getStatus() {
        return this.successful;
    }
}
