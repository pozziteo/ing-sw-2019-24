package adrenaline.data.data_for_client.responses_for_view;

import java.io.Serializable;
import java.util.List;

public abstract class SquareDetails implements Serializable {
    private int id;
    private List<String> playersOnSquare;
    private boolean isSpawnPoint;

    public SquareDetails(int id, List<String> playersOnSquare, boolean isSpawnPoint) {
        this.id = id;
        this.playersOnSquare = playersOnSquare;
        this.isSpawnPoint = isSpawnPoint;
    }

    public int getId() {
        return this.id;
    }

    public List<String> getPlayersOnSquare() {
        return this.playersOnSquare;
    }

    public boolean isSpawnPoint() {
        return this.isSpawnPoint;
    }
}
