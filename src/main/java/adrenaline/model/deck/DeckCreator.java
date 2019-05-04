package adrenaline.model.deck;

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