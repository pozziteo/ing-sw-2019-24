package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.List;

public class Targets {
    private int dimension;
    private List<Player> targets;

    public Targets(int n) {
        this.dimension = n;
    }

    public Targets(int n, List<Player> targets) {
        this.dimension = n;
        this.targets = targets;
    }

    public void setTargets(Player p) {
        if (targets.size() < dimension) {
            targets.add(p);
        }
    }

    public List<Player> getTargets() {
        return this.targets;
    }
}
