package model.player;

import model.map.*;
import model.map.Map;

import java.util.*;

//TODO add JavaDoc
public class Move implements Action {

    private List<Integer> paths;

    public Move(Player player) {
        System.out.println("You can move into squares:\n");
        Map map = player.getGame().getArena();
        Square position = player.getPosition();
        List<Integer> toVisit = new ArrayList<>();
        toVisit.add(position.getSquareId());
        List<Integer> visited = new ArrayList<>();
        for (int moves = 1; moves <= 3; moves++) {
            System.out.print("[");
            int currentNodes = toVisit.size();
            while (currentNodes > 0) {
                position = map.getSquare(toVisit.remove(0));
                for (int squareId : position.getLinks()) {
                    if (!visited.contains(squareId) && squareId != player.getPosition().getSquareId()) {
                        System.out.print(" (" + squareId/4 + ", " + squareId%4 + ") ");
                        visited.add(squareId);
                        toVisit.add(squareId);
                    }
                }
                currentNodes--;
            }
            System.out.println(" ] (" + moves + " movements)");
        }
        this.paths = visited;
    }

    public List<Integer> getPaths() {
        return this.paths;
    }

    public Square takeMove(Player player, int xSquare, int ySquare) {
        int squareId = xSquare*4 + ySquare;
        if (paths.contains(squareId))
            player.setPosition(player.getGame().getArena().getSquare(squareId));
        else player.setPosition(player.getPosition());

        return player.getPosition();
    }

    @Override
    public String getActionInfo() {
        return Action.super.getActionInfo() + "With this action you can move through up to 3 squares";
    }

    @Override
    public void executeAction(Player player) {
        //TODO
    }
}
