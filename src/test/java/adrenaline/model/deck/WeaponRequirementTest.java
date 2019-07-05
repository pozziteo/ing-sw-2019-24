package adrenaline.model.deck;

import adrenaline.model.Game;
import adrenaline.model.player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class WeaponRequirementTest {
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
    void testRequirements() {
        game.setArena (SMALL);
        p1.setPosition(game.getMap ().getSquare(2));
        p2.setPosition(game.getMap ().getSquare(4));
        p3.setPosition(game.getMap ().getSquare(6));
        p4.setPosition(game.getMap ().getSquare(1));
        p5.setPosition(game.getMap ().getSquare(10));
        game.startGame ();

        WeaponEffectRequirement requirement;

        List<Player> targets = new ArrayList<> (game.getPlayers ());
        targets.remove (p1);
        requirement = new NullRequirement ();
        assertTrue(targets.containsAll (requirement.findTargets (p1)));

        targets = new ArrayList<> ();
        targets.add(p3);
        targets.add(p4);
        targets.add(p5);
        requirement = new DirectionRequirement ();
        assertTrue(targets.containsAll (requirement.findTargets (p1)));

        requirement = new DistanceRequirement (2, 2);
        assertTrue(requirement.findTargets (p1).isEmpty ());

        targets = new ArrayList<> ();
        targets.add(p3);
        targets.add(p4);
        requirement = new DistanceRequirement (0, 1);
        assertTrue(targets.containsAll (requirement.findTargets (p1)));

        targets = new ArrayList<> ();
        targets.add(p3);
        targets.add(p4);
        targets.add(p2);
        targets.add(p5);
        requirement = new DistanceRequirement (1, 0);
        assertTrue(targets.containsAll (requirement.findTargets (p1)));

        targets = new ArrayList<> ();
        targets.add(p3);
        targets.add(p4);
        requirement = new LimitedDirectionRequirement (1);
        assertTrue(targets.containsAll (requirement.findTargets (p1)));

        targets = new ArrayList<> ();
        targets.add(p3);
        targets.add(p4);
        targets.add(p2);
        requirement = new MoveToVisibleRequirement (1);
        assertTrue(targets.containsAll (requirement.findTargets (p1)));

        targets = new ArrayList<> ();
        targets.add(p4);
        requirement = new RoomRequirement ("same");
        assertTrue(targets.containsAll (requirement.findTargets (p1)));

        targets = new ArrayList<> ();
        targets.add(p2);
        targets.add(p3);
        targets.add(p5);
        requirement = new RoomRequirement ("different");
        assertTrue(targets.containsAll (requirement.findTargets (p1)));

        targets = new ArrayList<> ();
        targets.add(p4);
        targets.add(p3);
        targets.add(p2);
        requirement = new VisibilityRequirement (true);
        assertTrue(targets.containsAll (requirement.findTargets (p1)));

        targets = new ArrayList<> ();
        targets.add(p5);
        requirement = new VisibilityRequirement (false);
        assertTrue(targets.containsAll (requirement.findTargets (p1)));
    }

}
