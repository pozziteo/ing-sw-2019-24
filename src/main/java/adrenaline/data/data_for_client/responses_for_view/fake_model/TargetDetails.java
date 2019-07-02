package adrenaline.data.data_for_client.responses_for_view.fake_model;

import adrenaline.data.data_for_client.DataForClient;

/**
 * This class contains information for targets shown to the client
 */

public class TargetDetails extends DataForClient {
    private int value;
    private boolean area;
    private int movements;

    public TargetDetails(int value, boolean area, int movements) {
        this.value = value;
        this.area = area;
        this.movements = movements;
    }

    /**
     * Getter method for value
     * @return value
     */

    public int getValue() {
        return this.value;
    }

    /**
     * Getter method to know if it's area
     * @return true if area, false otherwise
     */

    public boolean isArea() {
        return this.area;
    }

    /**
     * Getter method for movements
     * @return movements
     */

    public int getMovements() {
        return this.movements;
    }
}
