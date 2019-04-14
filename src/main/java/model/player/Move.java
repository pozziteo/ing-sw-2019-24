package model.player;

import model.map.*;
import model.map.Map;

import java.util.*;

//TODO add JavaDoc
public class Move implements Action {

    private List<Integer> paths;

    public Move(Player player) {
        System.out.println("You can move into squares:\n");
        paths = findPaths(player, 3);
    }

    public List<Integer> getPaths() {
        return this.paths;
    }

    public Square takeMove(Player player, int xSquare, int ySquare) {
        int squareId = xSquare*4 + ySquare;
        if (paths.contains(squareId)) {
            player.setPosition(player.getGame().getArena().getSquare(squareId));
            executedAction(player);
        }
        else player.setPosition(player.getPosition());

        return player.getPosition();
    }

    @Override
    public String getActionInfo() {
        return Action.super.getActionInfo() + "With this action you can move through up to 3 squares.";
    }

    @Override
    public void executedAction(Player player) {
        Action.super.executedAction(player);
    }
}
