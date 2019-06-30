package adrenaline.exceptions;

/**
 * This exception is thrown when a player uses a power up effect in the wrong way.
 */

public class IllegalUseOfPowerUpException extends Exception {
    public IllegalUseOfPowerUpException(String message){super(message);}
}
