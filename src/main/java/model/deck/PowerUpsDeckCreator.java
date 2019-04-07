package model.deck;

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
            PowerUp powerUp = new PowerUp(type);
            powerUpsDeck.addCard(powerUp);
        }

        powerUpsDeck.deckShuffle();

        return powerUpsDeck;
    }

}