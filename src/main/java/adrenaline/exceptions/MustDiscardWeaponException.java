package adrenaline.exceptions;

public class MustDiscardWeaponException extends Exception {
    public MustDiscardWeaponException() {
        super("Error: you can't hold more than three weapons. Please discard one.");
    }
}
