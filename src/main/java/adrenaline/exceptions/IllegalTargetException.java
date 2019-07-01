package adrenaline.exceptions;

/**
 * This exception is thrown when a player tries to hit other players that are not
 * reachable with the weapon's effect.
 */

public class IllegalTargetException extends Exception {

    public IllegalTargetException() {
        super("Error: You cannot hit these targets. Automatically skipping this action...");
    }
}
