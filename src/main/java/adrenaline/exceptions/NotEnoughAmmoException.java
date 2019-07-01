package adrenaline.exceptions;

/**
 * This exception is thrown when a player tries to perform an action that requires
 * ammo but they don't have enough to pay the cost.
 */

public class NotEnoughAmmoException extends Exception {

    public NotEnoughAmmoException(String message) {
        super(message);
    }
}
