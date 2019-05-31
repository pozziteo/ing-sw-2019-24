package adrenaline.data.data_for_client.responses_for_view;

import java.io.Serializable;

public class PowerUpDetails implements Serializable {
    private String type;
    private String color;

    public PowerUpDetails(String type, String color) {
        this.type = type;
        this.color = color;
    }

    public String getType() {
        return this.type;
    }

    public String getColor() {
        return this.color;
    }
}
