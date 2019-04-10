package model.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck is the supertype for model.deck.WeaponsDeck, model.deck.TilesDeck and model.deck.PowerUpsDeck.
 */

public abstract class Deck {
    /**
     * cards is the list of cards that makes up a certain deck, it represents the pool of cards a player can draw from.
     */
    private List<Card> cards;

    /**
     * drawnCards is the list of cards that have already been drawn from the deck and are not available to other players anymore.
     */
    private List<Card> drawnCards;

    /**
     * Constructor for model.deck.Deck. Doesn't initialize the attributes.
     */
    protected Deck() {
        cards = new ArrayList<>();
        drawnCards = new ArrayList<>();
    }

    /**
     * Getter method that returns the list of cards a player can draw from.
     * @return cards
     */

    public List<Card> getCards() {
        return cards;
    }

    /**
     * Getter method that returns the list of cards a player has already drawn.
     * @return drawnCards
     */

    public List<Card> getDrawnCards() {
        return drawnCards;
    }

    /**
     * addCard() is a method to add a model.deck.Card to the cards list of a model.deck.Deck.
     * @param card
     */

    protected void addCard(Card card) {
        this.getCards ().add(card);
    }

    /**
     * drawCard() draws a model.deck.Card from a model.deck.Deck.
     * @return card that has been drawn.
     */

    public Card drawCard() {
        if (this.getCards ().isEmpty()) {
            return null;
        }
        return this.getCards ().remove(this.getCards ().size() - 1);
    }

    /**
     * discardCard() lets you add a used model.deck.Card to the drawnCards list.
     * @param card
     */

    public void discardCard(Card card) {
        this.getDrawnCards ().add(card);
    }

    /**
     * deckShuffle() shuffles the list of cards of a model.deck.Deck.
     */

    public void deckShuffle() {
        Collections.shuffle(this.getCards ());
    }

    /**
     * reloadDeck adds every Card in drawnCards to the cards list of a model.deck.Deck and shuffles them.
     */

    public void reloadDeck() {
        for (Card currentCard : this.getDrawnCards ()) {
            this.getCards().add(currentCard);
        }
        this.getDrawnCards ().clear();
        this.deckShuffle();
    }

}