package adrenaline.model.player;

import adrenaline.model.map.Map;
import adrenaline.model.map.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Action is implemented by other classes
 * It contains the methods to find the possible paths, the action information and the weapons needed for the actions
 */
public interface Action {

    /**
     * Method to add this action to the list of executed actions of a player
     * @param player that executed this action
     */
    default void executedAction(Player player) {
        Action[] actions = player.getPerformedActions();
        for (int i=0; i < actions.length; i++) {
            if (actions[i] == null) {
                actions[i] = this;
                break;
            }
        }
    }

    /**
     * Method to find possible paths a player can take
     * @param player moving
     * @param movements possible
     * @return list of paths
     */

    static List<Integer> findPaths(Player player, int movements) {

        Map map = player.getGame().getMap();
        Square position = player.getPosition();

        return findPaths (map, position, movements);
    }

    /**
     * Method to build the paths
     * @param map of the game
     * @param position of the player
     * @param movements possible
     * @return paths
     */

    static List<Integer> findPaths(Map map, Square position, int movements) {
        List<Integer> toVisit = new ArrayList<>();
        toVisit.add(position.getSquareId());
        List<Integer> visited = new ArrayList<>();
        visited.add (position.getSquareId ());
        for (int moves = 1; moves <= movements; moves++) {
            int currentNodes = toVisit.size();
            while (currentNodes > 0) {
                position = map.getSquare(toVisit.remove(0));
                for (int squareId : position.getLinks()) {
                    if (!visited.contains(squareId)) {
                        visited.add(squareId);
                        toVisit.add(squareId);
                    }
                }
                currentNodes--;
            }
        }
        return visited;
    }

    default String getActionInfo() {
        return "Action information:\n";
    }

}
