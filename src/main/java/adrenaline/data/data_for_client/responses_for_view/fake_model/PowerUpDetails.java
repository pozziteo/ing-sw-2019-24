package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;

/**
 * This class contains information for power ups shown to the client
 */

public class PowerUpDetails implements Serializable {
    private String type;
    private String color;

    public PowerUpDetails(String type, String color) {
        this.type = type;
        this.color = color;
    }

    /**
     * Getter method for power up type
     * @return type
     */

    public String getType() {
        return this.type;
    }

    /**
     * Getter method for color
     * @return color
     */

    public String getColor() {
        return this.color;
    }
}
