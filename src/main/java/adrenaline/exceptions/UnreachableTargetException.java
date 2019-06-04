package adrenaline.exceptions;

public class UnreachableTargetException extends Exception {

    public UnreachableTargetException() {
        super("You cannot hit these targets.");
    }
}
