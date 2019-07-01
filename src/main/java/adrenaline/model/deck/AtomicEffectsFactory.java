package adrenaline.model.deck;

import adrenaline.model.player.Action;
import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class AtomicEffectsFactory {

    public AtomicEffectsFactory() {
        super();
    }

    /**
     * Method to create the base effect of a weapon
     * @param pureDamage is amount of damage
     * @param marks is the number of marks
     * @return the base effect
     */
    public AtomicWeaponEffect createBaseDamageEffect(int pureDamage, int marks) {
         return (attacker, target, id) ->  {
            target.getBoard().gotHit(pureDamage, attacker);
            int previousMarks = target.getBoard().getMarksAmountGivenByPlayer(attacker);
            for (int i=0; i < previousMarks; i++)
                target.getBoard().getReceivedMarks().remove(attacker.getPlayerColor());
            target.getBoard().gotHit(previousMarks, attacker);
            target.getBoard().gotMarked(marks, attacker);
        };
    }

    /**
     * Method to create an additional damage effect
     * @param pureDamage is the amount of damage
     * @return the additional effect
     */
    public AtomicWeaponEffect createAdditionalDamageEffect(int pureDamage) {
        return (attacker, target, id) -> target.getBoard().gotHit(pureDamage, attacker);
    }

    /**
     * Method to create a base effect to apply to a single square
     * @param pureDamage is the amount of damage
     * @param marks is the number of marks
     * @return the square effect
     */
    public AtomicWeaponEffect createSquareBasedDamage(int pureDamage, int marks) {
        return (attacker, target, id) -> {
            List<Player> players = new ArrayList<>(attacker.getGame().getPlayers());
            players.remove(attacker);
            if (id == null) {
                id = new Integer[1];
                id[0] = attacker.getPosition ( ).getSquareId ( );
            }
            for (Player player : players)
                if (player.getPosition().getSquareId() == id[0]) {
                    AtomicWeaponEffect effect = createBaseDamageEffect(pureDamage, marks);
                    effect.applyEffect(attacker, player, id);
                }
        };
    }

    /**
     * Method to create a base effect to apply to a whole room
     * @param pureDamage is the amount of damage
     * @param marks is the number of marks
     * @return the base effect
     */
    public AtomicWeaponEffect createRoomBasedDamage(int pureDamage, int marks) {
        return (attacker, target, id) -> {
            List<Player> players = new ArrayList<>(attacker.getGame().getPlayers());
            players.remove(attacker);
            String roomColor = attacker.getGame().getMap().getSquare(id[0]).getSquareColor();
            List<Player> playersInRoom = attacker.getGame().getMap().getPlayersInRoom(roomColor, players);

            for (Player player : playersInRoom) {
                AtomicWeaponEffect effect = createBaseDamageEffect(pureDamage, marks);
                effect.applyEffect(attacker, player, id);
            }
        };
    }

    /**
     * Method to create a movement after a hit
     * @param executor is the name of the attacker
     * @param movements is the number of movements
     * @return the generic movement
     */
    public AtomicWeaponEffect createGenericMovementEffect(String executor, int movements) {
        return (attacker, target, id) -> {
            Player performer;
            List<Integer> paths;
            if (executor.equals("attacker")) {
                paths = Action.findPaths(attacker, movements);
                performer = attacker;
            }
            else {
                paths = Action.findPaths(target, movements);
                performer = target;
            }

            if (id[0] != null && paths.contains(id[0])) {
                performer.getPosition ().removePlayerFromSquare (performer);
                performer.setPosition (performer.getGame ( ).getMap ( ).getSquare (id[0]));
            }
        };
    }

    /**
     * Method to create a movement that moves a victim to the attacker's square
     * @return the movement
     */
    public AtomicWeaponEffect createMoveToAttackerPosition() {
        return (attacker, target, id) -> {
            target.getPosition ().removePlayerFromSquare (target);
            target.setPosition(attacker.getPosition());
        };
    }

    /**
     * Method to create a movement that moves the attacker to the target's square
     * @return
     */
    public AtomicWeaponEffect createMoveToTargetPosition() {
        return (attacker, target, id) -> {
            attacker.getPosition ().removePlayerFromSquare (attacker);
            attacker.setPosition(target.getPosition());
        };
    }

    /**
     * Method to create a movement that move the targets to a visible square
     * @param maxMovements is the maximum number of movements
     * @return the movement
     */
    public AtomicWeaponEffect createMoveTargetToVisibleSquare(int maxMovements) {
        return (attacker, target, id) -> {
            List<Integer> targetPaths = Action.findPaths(target, maxMovements);
            if (targetPaths.contains(id[0]) && attacker.canSee(attacker.getGame().getMap().getSquare(id[0]))) {
                target.getPosition ().removePlayerFromSquare (target);
                target.setPosition (target.getGame ( ).getMap ( ).getSquare (id[0]));
            }
        };
    }

    /**
     * Method to create a movement that move the executor to a new square
     * @param executor is the name of the executor
     * @return the movement
     */
    public AtomicWeaponEffect createMoveToSquare(String executor) {
        return (attacker, target, id) -> {
            Player performer;
            if (executor.equals("attacker"))
                performer = attacker;
            else
                performer = target;

            performer.getPosition ().removePlayerFromSquare (performer);
            performer.setPosition(performer.getGame().getMap().getSquare(id[0]));
        };
    }

}
