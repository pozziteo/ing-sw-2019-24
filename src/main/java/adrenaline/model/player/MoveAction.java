package adrenaline.model.player;

import adrenaline.model.map.Square;

import java.util.*;

/**
 * Implementation of Action interface, it contains the Move Action methods
 */
public class MoveAction implements Action {

    private List<Integer> paths;

    /**
     * Method that create the action move
     * @param player is the player
     * @param frenzy is the flag of finalfrenzy (true if the game is in the final frenzy, false otherwise)
     */
    public MoveAction(Player player, boolean frenzy) {

        if (!frenzy)
            paths = Action.findPaths(player, 3);
        else if (player.getGame ().isBeforeFirstPlayer (player)){
            paths = Action.findPaths(player, 4);
        }
    }

    /**
     * Getter method
     * @return the list of possible paths for the move action
     */
    public List<Integer> getPaths() {
        return this.paths;
    }

    /**
     * Method that sets the player to the new squareID
     * @param player is the player
     * @param squareId is the new position
     * @return the new position of the player
     */
    public Square performMovement(Player player, int squareId) {
        if (paths.contains(squareId)) {
            player.getPosition ().removePlayerFromSquare(player);
            player.setPosition (player.getGame ().getMap ().getSquare (squareId));
            executedAction(player);
        }
        else player.setPosition(player.getPosition());

        return player.getPosition();
    }

    /**
     * Getter method
     * @return the action information
     */
    @Override
    public String getActionInfo() {
        return Action.super.getActionInfo() + "With this action you can move through up to 3 squares.";
    }

    /**
     * Method to execute the action
     * @param player is the player
     */
    @Override
    public void executedAction(Player player) {
        Action.super.executedAction(player);
    }
}
