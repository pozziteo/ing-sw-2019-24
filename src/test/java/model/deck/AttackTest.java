package model.deck;

import model.Game;
import model.player.*;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttackTest {
    @Test
    public void testAttack() throws FileNotFoundException {
        Game game = new Game(3);
        game.setArena ("maps\\smallmap.json");
        Player p1 = new Player(game, "red");
        Player p2 = new Player(game, "blue");

        p1.setPosition(game.getArena().getSquare(2));
        p2.setPosition(game.getArena().getSquare(4));

        Attack a = new Attack(2, 1, p1, p2);
        a.giveDamage ();
        a.giveMarks ();
        a = new Attack(1, 0, p1, p2);
        a.giveDamage ();
        assertEquals(3, p2.getBoard ().getDamageAmountGivenByPlayer (p1));
        assertEquals(1, p2.getBoard ().getMarksAmountGivenByPlayer (p1));

    }
}
