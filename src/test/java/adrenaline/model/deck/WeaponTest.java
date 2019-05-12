package adrenaline.model.deck;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeaponTest {

    @Test
    void checkEffects() {
        WeaponsDeck deck = new WeaponsDeckCreator().createDeck();
        List<Card> weapons = deck.getCards();

        for (Card card : weapons) {
            Weapon weapon = (Weapon) card;
            assertNotNull(weapon.getBaseEffect());
            if (weapon.getWeaponsName().equals("Heatseeker") || weapon.getWeaponsName().equals("Whisper")) {
                assertTrue(weapon.getOptionalEffects().isEmpty());
            }
            else {
                assertFalse(weapon.getOptionalEffects().isEmpty());
            }
        }
    }

}