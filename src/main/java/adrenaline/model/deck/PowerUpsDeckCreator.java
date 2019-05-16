package adrenaline.model.deck;

import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.deck.powerup.PowerUpType;

public class PowerUpsDeckCreator extends DeckCreator {

    /**
     * This method calls the superclass constructor
     */
    public PowerUpsDeckCreator(){
        super();
    }

    /**
     * This method creates a PowerUpsDeck
     * @return PowerUpsDeck
     */
    public PowerUpsDeck createDeck(){

        PowerUpsDeck powerUpsDeck = new PowerUpsDeck ();


        for (PowerUpType type : PowerUpType.values()){
            for (Ammo ammoColor: Ammo.values()) {
                PowerUp powerUp = new PowerUp(type, ammoColor);
                powerUpsDeck.addCard(powerUp);
                powerUp = new PowerUp(type, ammoColor);
                powerUpsDeck.addCard(powerUp);
            }
        }

        powerUpsDeck.deckShuffle();

        return powerUpsDeck;
    }

}