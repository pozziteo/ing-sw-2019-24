package model.player;

import model.map.*;
import model.deck.*;

import java.util.ArrayList;
import java.util.List;

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

    default List<Integer> findPaths(Player player, int movements) {

        Map map = player.getGame().getArena();
        Square position = player.getPosition();
        List<Integer> toVisit = new ArrayList<>();
        toVisit.add(position.getSquareId());
        List<Integer> visited = new ArrayList<>();
        for (int moves = 1; moves <= movements; moves++) {
            System.out.print("[");
            int currentNodes = toVisit.size();
            while (currentNodes > 0) {
                position = map.getSquare(toVisit.remove(0));
                for (int squareId : position.getLinks()) {
                    if (!visited.contains(squareId) && squareId != player.getPosition().getSquareId()) {
                        System.out.print(" (" + squareId / 4 + ", " + squareId % 4 + ") ");
                        visited.add(squareId);
                        toVisit.add(squareId);
                    }
                }
                currentNodes--;
            }
            System.out.println(" ] (" + moves + " movements)");
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
