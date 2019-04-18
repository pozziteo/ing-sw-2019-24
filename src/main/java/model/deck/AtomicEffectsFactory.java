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
         return (attacker, target) -> {
            target.getBoard().gotHit(pureDamage, attacker);
            int previousMarks = attacker.getBoard().getMarksAmountGivenByPlayer(attacker);
            target.getBoard().gotHit(previousMarks, attacker);
            target.getBoard().gotMarked(marks, attacker);
        };
    }

    public AtomicWeaponEffect createMovementEffect(String executor, int movements) {
        return (attacker, target) -> {
            Player performer;
            List<Integer> paths;
            if (executor.equals("attacker")) {
                System.out.println("You can move into squares:");
                paths = findPaths(attacker, movements);
                performer = attacker;
            }
            else {
                System.out.println("You can move your target into squares:");
                paths = findPaths(target, movements);
                performer = target;
            }
            //TODO find a way to get and set the new position
        };
    }




}
