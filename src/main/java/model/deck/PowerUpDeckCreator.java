package model.deck;

public class PowerUpDeckCreator extends DeckCreator {

    /**
     * this class calls the superclass constructor
     */
    protected PowerUpDeckCreator(){
        super();
    }

    /**
     * This method create a new shuffled PowerUpDeck
     * return a PowerUpDeck
     */
    public PowerUpDeck createDeck(){

        PowerUpDeck powerUpDeck = new PowerUpDeck();

        for (PowerUpType type : PowerUpType.values()){
            PowerUp powerUp = new PowerUp(type);
            powerUpDeck.addCard(powerUp);
        }

        powerUpDeck.deckShuffle();

        return powerUpDeck;
    }

}