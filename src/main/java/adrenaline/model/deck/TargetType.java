package adrenaline.model.deck;

import adrenaline.model.player.Action;
import adrenaline.model.player.Player;
import adrenaline.model.player.ShootAction;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Class that specifies all the different type of targets
 */
public class TargetType {

    private int targetValue;
    private boolean areaType;
    private int movements;
    private ArrayList<String> constraints;

    protected TargetType(int value, boolean area, int movements, ArrayList<String> constraints) {
        this.targetValue = value;
        this.areaType = area;
        this.movements = movements;
        this.constraints = new ArrayList<> (constraints);
    }

    /**
     * Getter method
     * @return the integer value of a target
     */
    public int getTargetValue() {
        return this.targetValue;
    }

    /**
     * Method to establish if a targetType is an area type
     * @return true if the target is an areaType target
     */
    public boolean isAreaType() {
        return this.areaType;
    }

    /**
     * Getter method
     * @return how many movements are possible for a target
     */
    public int getMovements() {
        return this.movements;
    }

    /**
     * Getter method
     * @return the list of constraints
     */
    public List<String> getConstraints() {
        return this.constraints;
    }

    /**
     * Method to establish if a player is a compliant target
     * @param attacker is the players attacking
     * @param players is the list of players in the game
     * @param squareId is the id of the square
     * @return true if a player is a compliant target, false if no players inside 'players' is a compliant target
     */
    public boolean isCompliantTargets(Player attacker, List<Player> players, int squareId) {
        for (String s : constraints) {
            if (!applyConstraints (s, attacker, players, squareId))
                return false;
        }
        return true;
    }

    /**
     * Method to establish what kind target the 'targets' are
     * @param constraint is the constraint of an effect
     * @param attacker is the player who is attacking
     * @param targets is the list of targets
     * @param squareId is the identifier of a square
     * @return
     */
    private boolean applyConstraints(String constraint, Player attacker, List<Player> targets, int squareId) {
        boolean legal = true;
        Action currentAction = attacker.getGame ().getCurrentAction ();
        switch (constraint) {

            case "square different from current position":
                if (squareId == attacker.getPosition ().getSquareId ())
                    legal = false;
                break;

            case "target at distance 1 from square":
                for (Player p : targets) {
                    int playerPos = p.getPosition ().getSquareId ();
                    int distance = abs(squareId -  playerPos);
                    if (! (distance == 1 || distance == 4 || distance == 0)) {
                        legal = false;
                        break;
                    }
                }
                break;

            case "different from base target":
                for (Player p : targets) {
                    if (((ShootAction)currentAction).getBaseEffect ().getTargets ().contains(p)) {
                        legal = false;
                        break;
                    }
                }
                break;

            case "on different squares":
                int attackerPosition = attacker.getPosition().getSquareId();
                for (Player p : targets) {
                    int playerPos = p.getPosition ().getSquareId ();
                    if (attackerPosition == playerPos) {
                        legal = false;
                        break;
                    }
                }
                break;

            case "on base target position":
                if (((ShootAction)currentAction).getBaseEffect ().getTargets ().get(0).getPosition ().getSquareId () != squareId) {
                        legal = false;
                        break;
                }
                break;

            case "on same direction":
                int attackerPositionX = attacker.getPosition().getX();
                int attackerPositionY = attacker.getPosition().getY();
                for (Player p: targets){
                    int playerPosX = p.getPosition().getX();
                    int playerPosY = p.getPosition().getY();
                    if (attackerPositionX!=playerPosX && attackerPositionY!=playerPosY){
                        legal = false;
                        break;
                    }
                }
                break;

            case "one target per square":
                for (Player p : targets) {
                    for (int i = targets.indexOf (p)+1; i < targets.size (); i++) {
                        if (targets.get(i).getPosition ().equals(p.getPosition ())) {
                            legal = false;
                            break;
                        }
                    }
                }
                break;

            case "one of base targets":
                if (!((ShootAction)currentAction).getBaseEffect ().getTargets ().contains (targets.get (0)))
                    legal = false;
                break;

            case "one of base targets different from first optional targets":
                if (!((ShootAction)currentAction).getBaseEffect ().getTargets ().contains (targets.get (0))) {
                    legal = false;
                    break;
                }
                if (targets.get(0).equals(((ShootAction)currentAction).getOptionalEffect ().getTargets ().get (0)))
                    legal = false;
                break;

            case "everyone around user":
                List<Player> playersAroundUser = new ArrayList<> ();
                for (Player p : attacker.getGame ().getPlayers ()) {
                    if (!p.equals(attacker)) {
                        int distance = abs(attacker.getPosition ().getSquareId () -  p.getPosition ().getSquareId ());
                        if (distance == 1 || distance == 4) {
                            playersAroundUser.add(p);
                        }
                    }
                }
                if (!targets.containsAll (playersAroundUser))
                    legal = false;
                break;

            default:
                break;
        }
        return legal;
    }
}
