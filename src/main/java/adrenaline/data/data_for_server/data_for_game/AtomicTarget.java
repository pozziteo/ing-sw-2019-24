package adrenaline.data.data_for_server.data_for_game;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains data about the targets chosen for each atomic weapon effect of the Shoot action
 */

public class AtomicTarget implements Serializable {
    private List<String> targetNames; //list of nicknames of the targets
    private int squareId; //id of the square chosen if effect requires movement or is of type area

    public AtomicTarget(List<String> names, int square) {
        this.targetNames = names;
        this.squareId = square;
    }

    /**
     * Getter method for targets' names
     * @return names
     */

    public List<String> getTargetNames() {
        return this.targetNames;
    }

    /**
     * Getter method for square id
     * @return id
     */

    public int getSquareId() {
        return this.squareId;
    }
}
