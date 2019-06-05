package adrenaline.data.data_for_server.data_for_game;

import java.io.Serializable;
import java.util.List;

public class AtomicTarget implements Serializable {
    private List<String> targetNames;
    private int squareId;

    public AtomicTarget(List<String> names, int square) {
        this.targetNames = names;
        this.squareId = square;
    }

    public List<String> getTargetNames() {
        return this.targetNames;
    }

    public int getSquareId() {
        return this.squareId;
    }
}
