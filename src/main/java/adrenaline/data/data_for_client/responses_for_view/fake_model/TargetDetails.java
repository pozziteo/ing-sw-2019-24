package adrenaline.data.data_for_client.responses_for_view.fake_model;

import adrenaline.data.data_for_client.DataForClient;

public class TargetDetails extends DataForClient {
    private int value;
    private boolean area;
    private int movements;

    public TargetDetails(int value, boolean area, int movements) {
        this.value = value;
        this.area = area;
        this.movements = movements;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isArea() {
        return this.area;
    }

    public int getMovements() {
        return this.movements;
    }
}
