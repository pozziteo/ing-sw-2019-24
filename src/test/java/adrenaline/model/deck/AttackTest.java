package adrenaline.model.deck;

import adrenaline.model.Game;
import adrenaline.model.deck.Attack;
import adrenaline.model.player.Player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttackTest {
    private Game game = new Game(3);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);

    @Test
    public void testAttack() {
        p1.setPosition(game.getMap ().getSquare(2));
        p2.setPosition(game.getMap ().getSquare(4));

        Attack a = new Attack(2, 1, p1, p2);
        a.giveDamage ();
        a.giveMarks ();
        a = new Attack(1, 0, p1, p2);
        a.giveDamage ();
        assertEquals(3, p2.getBoard ().getDamageAmountGivenByPlayer (p1));
        assertEquals(1, p2.getBoard ().getMarksAmountGivenByPlayer (p1));

    }
}
