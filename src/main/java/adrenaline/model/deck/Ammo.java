package adrenaline.model.deck;

/**
 * Ammo is the enumeration class which defines the three different kinds of ammo.
 */

public enum Ammo {

    RED_AMMO("Red"),
    BLUE_AMMO("Blue"),
    YELLOW_AMMO("Yellow");

    /**
     * color is the attribute which defines the kind of the ammo
     */
    private String color;

    /**
     * Constructor which initialize the ammo's color using the setter method.
     *
     * @param color which is the ammo's color
     */
    Ammo(String color) {
        this.setColor(color);
    }

    /** Setter to initialize the color of the ammo.
     *
     * @param color which is the ammo's color (red, yellow or blue)
     */
    private void setColor(String color) {
        this.color = color;
    }

    /**
     * Getter to obtain the color of an ammo
     *
     * @return the ammo's color
     */
    public String getColor() {
        return color;
    }
}
