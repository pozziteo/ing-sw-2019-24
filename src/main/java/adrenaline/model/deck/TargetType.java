package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class TargetType {

    private enum Type {
        NONE(""),
        SINGLE("single"),
        MULTIPLE("multiple");

        private String typeIdentifier;

        Type(String typeIdentifier) {
            this.typeIdentifier = typeIdentifier;
        }
    }

    private Type type;
    private boolean all;
    private int value;
    private ArrayList<String> constraints;

    protected TargetType() {
        this.type = Type.NONE;
        this.value = -1;
        this.all = false;
        this.constraints = new ArrayList<> ();
    }

    protected TargetType(String typeIdentifier, String targetValue, ArrayList<String> constraints) {
        if (typeIdentifier.equals("single")) {
            this.type = Type.SINGLE;
        } else if (typeIdentifier.equals("multiple")) {
            this.type = Type.MULTIPLE;
        }

        if (targetValue.equals("")) {
            this.all = false;
            this.value = -1;
        } else if (targetValue.equals("all")) {
            this.all = true;
            this.value = -1;
        } else {
            this.all = false;
            this.value = Integer.parseInt(targetValue);
        }

        this.constraints = new ArrayList<> (constraints);
    }

    public String getType() {
        return this.type.typeIdentifier;
    }

    public boolean isAll() {
        return this.all;
    }

    public int getValue() {
        return this.value;
    }

    public ArrayList<String> getConstraints() {
        return this.constraints;
    }

    public Targets findTargets(Player attacker, List<Player> players) {
        Targets targets;

        switch(type.typeIdentifier) {
            case "single":
                targets = new Targets(1);
                for (String s : constraints) {
                    applyConstraints (s, attacker, players);
                }
                break;

            case "multiple":
                targets = new Targets (value);
                for (String s : constraints) {
                    applyConstraints (s, attacker, players);
                }
                break;

            default:
                targets = new Targets(0, null);
                break;
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
                attacker.getCurrentAction();
                break;

            case "on different squares":

                break;

            case "on base target position":
                //TODO
                break;

            case "on same direction":
                //TODO
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
