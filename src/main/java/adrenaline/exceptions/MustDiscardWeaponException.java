package adrenaline.exceptions;

/**
 * This exception is thrown when a player exceeds the maximum number of weapons they can hold.
 */

public class MustDiscardWeaponException extends Exception {
    public MustDiscardWeaponException() {
        super("Error: you can't hold more than three weapons. Please discard one.");
    }
}
