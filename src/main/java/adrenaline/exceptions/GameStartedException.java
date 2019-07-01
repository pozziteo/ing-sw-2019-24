package adrenaline.exceptions;

/**
 * This exception is thrown when a client tries to access a lobby in which the game
 * has already started.
 */

public class GameStartedException extends Exception {

    public GameStartedException(String message) {
        super(message);
    }
}
