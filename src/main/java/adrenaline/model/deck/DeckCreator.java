package adrenaline.model.deck;

/**
 * Abstract class for the deck creation, implemented by subclass
 */
public abstract class DeckCreator {

        /**
         * Calls superclass constructor
         */
        protected DeckCreator() {
                super();
        }

        /**
         * Abstract method, implemented by subclass
         * @return deck
         */

        public abstract Deck createDeck();
}