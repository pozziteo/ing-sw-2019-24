package adrenaline.model.deck;

import java.io.Serializable;

/**
 * Card is the supertype for Weapon, Tile and PowerUp.
 */

public abstract class Card implements Serializable {

    private static final long serialVersionUID = 5457394979796635472L;

    /**
     * Constructor for Card.
     */
    protected Card () {
    }
}