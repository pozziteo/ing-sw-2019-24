package model.deck;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDeck {

    @Test
    public void testWeaponsDeckSize() {
        WeaponsDeckCreator deckCreator = new WeaponsDeckCreator();
        WeaponsDeck d = deckCreator.createDeck();

        assertEquals(d.getCards().size(), 21);

    }
}