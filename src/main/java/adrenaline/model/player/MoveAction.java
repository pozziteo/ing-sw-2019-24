package adrenaline.model.player;

import adrenaline.model.map.Square;

import java.util.*;

public class MoveAction implements Action {

    private List<Integer> paths;

    public MoveAction(Player player, boolean frenzy) {
        List<Player> players = player.getGame().getPlayers();
        Player firstPlayer = player.getGame().getFirstPlayer();

        if (!frenzy)
            paths = Action.findPaths(player, 3);
        else if (!player.equals(firstPlayer) &&
                players.indexOf(player) < players.indexOf(firstPlayer)){
            paths = Action.findPaths(player, 4);
        }
    }

    public List<Integer> getPaths() {
        return this.paths;
    }

    public Square performMovement(Player player, int squareId) {
        if (paths.contains(squareId)) {
            player.getPosition ().removePlayerFromSquare(player);
            player.setPosition (player.getGame ().getMap ().getSquare (squareId));
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
