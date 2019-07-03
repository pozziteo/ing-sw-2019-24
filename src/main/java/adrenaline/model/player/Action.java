package adrenaline.model.player;

import adrenaline.model.deck.Weapon;
import adrenaline.model.map.Map;
import adrenaline.model.map.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Action is implemented by other classes
 * It contains the methods to find the possible paths, the action information and the weapons needed for the actions
 */
public interface Action {

    default void executedAction(Player player) {
        Action[] actions = player.getPerformedActions();
        for (int i=0; i < actions.length; i++) {
            if (actions[i] == null) {
                actions[i] = this;
                break;
            }
        }
    }

    static List<Integer> findPaths(Player player, int movements) {

        Map map = player.getGame().getMap();
        Square position = player.getPosition();

        return findPaths (map, position, movements);
    }

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

    default void getWeapons(Player player) {
        List<Weapon> weapons = player.getOwnedWeapons ();

        for (int i = 0; i < weapons.size (); i++) {
            System.out.println (weapons.get(i).getWeaponsName ());
        }
    }

    default String getActionInfo() {
        return "Action information:\n";
    }

}
