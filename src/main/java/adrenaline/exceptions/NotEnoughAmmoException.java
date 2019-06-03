package adrenaline.exceptions;

public class NotEnoughAmmoException extends Exception {

    public NotEnoughAmmoException() {
        super("You don't have enough ammo to grab/reload this weapon or perform this action.\n");
    }
}
