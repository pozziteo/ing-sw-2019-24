package adrenaline.model.player;

import adrenaline.data.data_for_server.data_for_game.AtomicTarget;
import adrenaline.exceptions.IllegalTargetException;
import adrenaline.exceptions.IllegalUseOfPowerUpException;
import adrenaline.exceptions.NotEnoughAmmoException;
import adrenaline.model.Game;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.WeaponType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class ShootActionTest {
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";
    private String[] playerNames = {"luca", "matteo", "sara", "foo", "bar"};
    private Game game = new Game (playerNames);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);
    private Player p3 = game.getPlayers ().get(2);
    private Player p4 = game.getPlayers ().get(3);
    private Player p5 = game.getPlayers ().get(4);

    @Test
    void testPlayerShoot() {
        this.game.setArena (SMALL);
        p1.setPosition(game.getMap ().getSquare(2));
        p2.setPosition(game.getMap ().getSquare(4));
        p3.setPosition(game.getMap ().getSquare(6));
        p4.setPosition(game.getMap ().getSquare(1));
        p5.setPosition(game.getMap ().getSquare(10));
        game.startGame ();

        ShootAction action = new ShootAction (p1);

        Weapon heatseeker = new Weapon(WeaponType.HEATSEEKER);
        action.setChosenWeapon (heatseeker);
        action.addBaseEffect (heatseeker.getBaseEffect ());
        assertFalse(action.isMustUseBase ());
        assertTrue(action.isEndAction ());
        List<String> targets = new ArrayList<> ();
        targets.add("luca");
        AtomicTarget target = new AtomicTarget (targets, -1);
        List<AtomicTarget> chosenTargets = new ArrayList<> ();
        chosenTargets.add(target);
        try {
            action.setEffectTargets (chosenTargets, null);
        } catch (NotEnoughAmmoException | IllegalTargetException | IllegalUseOfPowerUpException e) {
            //TEST
        }
        assertEquals(3, p5.getBoard ().getDamageAmountGivenByPlayer (p1));
    }
}
