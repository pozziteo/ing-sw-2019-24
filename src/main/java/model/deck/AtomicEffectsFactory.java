package model.deck;

import model.player.Action;
import model.player.Move;
import model.player.Player;

import java.util.List;

public class AtomicEffectsFactory implements Action {

    public AtomicEffectsFactory() {
        super();
    }

    public AtomicWeaponEffect createDamageEffect(int pureDamage, int marks) {
         return (attacker, target, id) ->  {
            target.getBoard().gotHit(pureDamage, attacker);
            int previousMarks = target.getBoard().getMarksAmountGivenByPlayer(attacker);
            target.getBoard().gotHit(previousMarks, attacker);
            target.getBoard().gotMarked(marks, attacker);
        };
    }

    public AtomicWeaponEffect createMovementEffect(String executor, int movements) {
        return (attacker, target, id) -> {
            Player performer;
            List<Integer> paths;
            if (executor.equals("attacker")) {
                paths = findPaths(attacker, movements);
                performer = attacker;
            }
            else {
                paths = findPaths(target, movements);
                performer = target;
            }

            if (id[0] != null && paths.contains(id[0]))
                performer.setPosition(performer.getGame().getArena().getSquare(id[0]));
        };
    }

}
