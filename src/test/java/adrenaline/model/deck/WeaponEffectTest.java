package adrenaline.model.deck;

import adrenaline.model.Game;
import adrenaline.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

class WeaponEffectTest {
    private static final String PATH = "/maps/";
    private static final String SMALL = PATH + "smallmap.json";
    private String[] playerNames = {"luca", "matteo", "sara", "foo", "bar"};
    private Game game = new Game (playerNames);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);
    private Player p3 = game.getPlayers ().get(2);
    private Player p4 = game.getPlayers ().get(3);
    private Player p5 = game.getPlayers ().get(4);
    private AtomicEffectsFactory factory = new AtomicEffectsFactory ();

    @Test
    void testEffects() {
        game.setArena (SMALL);
        p1.setPosition(game.getMap ().getSquare(2));
        p2.setPosition(game.getMap ().getSquare(4));
        p3.setPosition(game.getMap ().getSquare(6));
        p4.setPosition(game.getMap ().getSquare(1));
        p5.setPosition(game.getMap ().getSquare(10));
        game.startGame ();

        AtomicWeaponEffect effect;

        effect = factory.createBaseDamageEffect (2, 1);
        effect.applyEffect (p1, p2, -1);
        assertEquals(2, p2.getBoard ().getDamageAmountGivenByPlayer (p1));
        assertEquals(1, p2.getBoard ().getMarksAmountGivenByPlayer (p1));

        effect = factory.createAdditionalDamageEffect (1);
        effect.applyEffect (p1, p2, -1);
        assertEquals(3, p2.getBoard ().getDamageAmountGivenByPlayer (p1));

        effect = factory.createSquareBasedDamage (2, 1);
        effect.applyEffect (p5, null, 6);
        assertEquals(2, p3.getBoard ().getDamageAmountGivenByPlayer (p5));
        assertEquals(1, p3.getBoard ().getMarksAmountGivenByPlayer (p5));

        effect = factory.createRoomBasedDamage (1, 0);
        effect.applyEffect (p5, null, 2);
        assertEquals(1, p1.getBoard ().getDamageAmountGivenByPlayer (p5));
        assertEquals(0, p1.getBoard ().getMarksAmountGivenByPlayer (p5));
        assertEquals(1, p4.getBoard ().getDamageAmountGivenByPlayer (p5));
        assertEquals(0, p4.getBoard ().getMarksAmountGivenByPlayer (p5));

        effect = factory.createMoveToAttackerPosition ();
        effect.applyEffect (p2, p4, (Integer[]) null);
        assertEquals (p4.getPosition (), p2.getPosition ());

        effect = factory.createMoveToTargetPosition ();
        effect.applyEffect (p1, p4, (Integer[]) null);
        assertEquals (p4.getPosition (), p1.getPosition ());

        p1.setPosition(game.getMap ().getSquare(2));
        p2.setPosition(game.getMap ().getSquare(4));
        p4.setPosition(game.getMap ().getSquare(1));

        effect = factory.createMoveTargetToVisibleSquare (2);
        effect.applyEffect (p1, p2, 1);
        assertEquals (1, p2.getPosition ().getSquareId ());

        effect = factory.createMoveToSquare ("target");
        effect.applyEffect (p1, p5, 11);
        assertEquals (11, p5.getPosition ().getSquareId ());
    }
}
