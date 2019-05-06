package adrenaline.model.deck;

import adrenaline.model.player.Action;
import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class AtomicEffectsFactory {

    public AtomicEffectsFactory() {
        super();
    }

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

    public AtomicWeaponEffect createAdditionalDamageEffect(int pureDamage) {
        return (attacker, target, id) -> target.getBoard().gotHit(pureDamage, attacker);
    }

    public AtomicWeaponEffect createSquareBasedDamage(int pureDamage, int marks) {
        return (attacker, target, id) -> {
            List<Player> players = new ArrayList<>(attacker.getGame().getPlayers());
            players.remove(attacker);
            for (Player player : players)
                if (player.getPosition().getSquareId() == id[0]) {
                    AtomicWeaponEffect effect = createBaseDamageEffect(pureDamage, marks);
                    effect.applyEffect(attacker, player, id);
                }
        };
    }

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

            if (id[0] != null && paths.contains(id[0]))
                performer.setPosition(performer.getGame().getMap().getSquare(id[0]));
        };
    }

    public AtomicWeaponEffect createMoveToAttackerPosition() {
        return (attacker, target, id) -> target.setPosition(attacker.getPosition());
    }

    public AtomicWeaponEffect createMoveToTargetPosition() {
        return (attacker, target, id) -> attacker.setPosition(target.getPosition());
    }

    public AtomicWeaponEffect createMoveTargetToVisibleSquare(int maxMovements) {
        return (attacker, target, id) -> {
            List<Integer> targetPaths = Action.findPaths(target, maxMovements);
            if (targetPaths.contains(id[0]) && attacker.canSee(attacker.getGame().getMap().getSquare(id[0])))
                target.setPosition(target.getGame().getMap().getSquare(id[0]));
        };
    }

    public AtomicWeaponEffect createMoveToSquare(String executor) {
        return (attacker, target, id) -> {
            Player performer;
            if (executor.equals("attacker"))
                performer = attacker;
            else
                performer = target;

            performer.setPosition(performer.getGame().getMap().getSquare(id[0]));
        };
    }

}
