package model.player;

import model.map.*;
import model.map.Map;

import java.util.*;

public class Move implements Action {

    @Override
    public String getActionInfo() {
        return Action.super.getActionInfo() + "With this action you can move through up to 3 squares";
    }

    public void findPaths(Player player) {
        System.out.println("You can move into squares:\n");
        Map map = Map.getInstance();
        Square position = player.getPosition();
        List<Integer> toVisit = new ArrayList<>();
        toVisit.add(position.getSquareId());
        List<Integer> visited = new ArrayList<>();
        for (int moves=1; moves <= 3; moves++ ) {
            position = map.getSquare(toVisit.remove(0));
            System.out.print("[");
            for (int squareId : position.getLinks()) {
                if (!visited.contains(squareId) && squareId != player.getPosition().getSquareId()) {
                    System.out.print(" " + squareId);
                    visited.add(squareId);
                    toVisit.add(squareId);
                }
            }
            System.out.print(" ] (" + moves + " movements)\n");
        }
    }

    @Override
    public void executeAction(Player player) {

    }
}
