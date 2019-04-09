package model.deck;

import model.Game;
import model.player.*;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttackTest {
    @Test
    public void testAttack() throws FileNotFoundException {
        Game game = Game.getGameInstance ( );
        game.setArena ("maps\\smallmap.json");
        Player p1 = new Player("red", game.getArena ().getSquare (2));
        Player p2 = new Player("blue", game.getArena ().getSquare (4));
        Attack a = new Attack(2, 1, p1, p2);
        a.giveDamage ();
        a.giveMarks ();
        a = new Attack(1, 0, p1, p2);
        a.giveDamage ();
        assertEquals(p2.getBoard ().getDamageAmountGivenByPlayer (p1), 3);
        assertEquals(p2.getBoard ().getMarksAmountGivenByPlayer (p1), 1);

    }
}
