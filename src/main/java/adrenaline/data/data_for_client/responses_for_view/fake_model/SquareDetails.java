package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains information for square shown to the client
 */

public abstract class SquareDetails implements Serializable {
    private int id;
    private List<String> playersOnSquare;
    private boolean isSpawnPoint;

    public SquareDetails(int id, List<String> playersOnSquare, boolean isSpawnPoint) {
        this.id = id;
        this.playersOnSquare = playersOnSquare;
        this.isSpawnPoint = isSpawnPoint;
    }

    /**
     * Getter method for id
     * @return id
     */

    public int getId() {
        return this.id;
    }

    /**
     * Getter method for players on square
     * @return players on square
     */

    public List<String> getPlayersOnSquare() {
        return this.playersOnSquare;
    }

    /**
     * Getter method to know if square is spawn point
     * @return true if spawn point, false otherwise
     */

    public boolean isSpawnPoint() {
        return this.isSpawnPoint;
    }
}
