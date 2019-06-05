package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

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

    public int getTargetValue() {
        return this.targetValue;
    }

    public boolean isAreaType() {
        return this.areaType;
    }

    public int getMovements() {
        return this.movements;
    }

    public List<String> getConstraints() {
        return this.constraints;
    }

    public Targets findCompliantTargets(Player attacker, List<Player> players) {
        Targets targets;

        targets = new Targets(targetValue);
        for (String s : constraints) {
            applyConstraints (s, attacker, players);
        }

        return targets;
    }

    private void applyConstraints(String constraint, Player attacker, List<Player> players) {
        switch (constraint) {

            case "square different from current position":
                for (Player p : players) {
                    if (p.getPosition ().equals(attacker.getPosition ()))
                        players.remove(p);
                }
                break;

            case "target at distance 1 from square":
                int attackerPos = attacker.getPosition ().getSquareId ();
                for (Player p : players) {
                    int playerPos = p.getPosition ().getSquareId ();
                    int distance = abs(attackerPos -  playerPos);
                    if (! (distance == 1 || distance == 4)) {
                        players.remove (p);
                    }
                }
                break;

            case "different from base target":
                //attacker.getCurrentAction();
                break;

            case "on different squares":
                int attackerPosition = attacker.getPosition().getSquareId();
                for (Player p : players) {
                    int playerPos = p.getPosition ().getSquareId ();
                    if (attackerPosition == playerPos) {
                        players.remove (p);
                    }
                }
                break;

            case "on base target position":
                //TODO
                break;

            case "on same direction":
                int attackerPositionX = attacker.getPosition().getX();
                int attackerPositionY = attacker.getPosition().getY();
                for (Player p: players){
                    int playerPosX = p.getPosition().getX();
                    int playerPosY = p.getPosition().getY();
                    if (attackerPositionX!=playerPosX && attackerPositionY!=playerPosY){
                        players.remove(p);
                    }
                }
                break;

            case "one target per square":
                //TODO
                break;

            case "one of base targets":
                //TODO
                break;

            case "one of base targets different from first optional targets":
                //TODO
                break;

            default:
                break;
        }
    }
}
