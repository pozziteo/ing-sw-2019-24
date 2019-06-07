package adrenaline.exceptions;

public class IllegalTargetException extends Exception {

    public IllegalTargetException() {
        super("Error: You cannot hit these targets. Automatically skipping this action...");
    }
}
