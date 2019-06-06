package adrenaline.exceptions;

public class MustUseBaseEffectException extends Exception {
    public MustUseBaseEffectException() {
        super("You must use the chosen weapon's base effect.");
    }
}
