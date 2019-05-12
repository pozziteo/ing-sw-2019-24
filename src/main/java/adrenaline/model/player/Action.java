package adrenaline.model.player;

import adrenaline.model.deck.Weapon;
import adrenaline.model.map.Map;
import adrenaline.model.map.Square;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface Action extends Serializable {

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

        return findPaths(map, position, movements);
    }

    static List<Integer> findPaths(Map map, Square position, int movements) {
        List<Integer> toVisit = new ArrayList<>();
        toVisit.add(position.getSquareId());
        List<Integer> visited = new ArrayList<>();
//        System.out.println("[ " + position.getSquareId() + " ] (0 movements)");
        for (int moves = 1; moves <= movements; moves++) {
//            System.out.print("[");
            int currentNodes = toVisit.size();
            while (currentNodes > 0) {
                position = map.getSquare(toVisit.remove(0));
                for (int squareId : position.getLinks()) {
                    if (!visited.contains(squareId)) {
//                        System.out.print(" (" + squareId / 4 + ", " + squareId % 4 + ") ");
                        visited.add(squareId);
                        toVisit.add(squareId);
                    }
                }
                currentNodes--;
            }
//            System.out.println(" ] (" + moves + " movements)");
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
