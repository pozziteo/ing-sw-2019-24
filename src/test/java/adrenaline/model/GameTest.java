package adrenaline.model;

import adrenaline.model.player.Player;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";
    private String[] playerNames = {"luca", "matteo", "sara", "pippo", "baudo"};
    private Game game = new Game (playerNames);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);
    private Player p3 = game.getPlayers ().get(2);
    private Player p4 = game.getPlayers ().get(3);
    private Player p5 = game.getPlayers ().get(4);
    private List<String> deathTrack = new ArrayList<>();
    private List<Player> ranking = new ArrayList<>();

    @Test
    void testPoints(){
        p1.getBoard().gotHit(3, p2);
        p1.getBoard().gotHit(2, p3);
        p1.getBoard().gotHit(5, p4);
        p1.getBoard().gotHit(1, p5);
        game.givePoints(p1);
        assertEquals(8, p4.getBoard().getPointTokens());
        assertEquals(7, p2.getBoard().getPointTokens());
        assertEquals(4, p3.getBoard().getPointTokens());
        assertEquals(2, p5.getBoard().getPointTokens());
    }

    @Test
    public void testID() {
        assertTrue (this.game.getGameID () != (new Random().nextInt() * 1000000));
    }

    @Test
    public void testWinner() {
        this.game.setArena (SMALL);
        p1.setPosition(game.getMap ().getSquare(2));
        p2.setPosition(game.getMap ().getSquare(4));
        p3.setPosition(game.getMap ().getSquare(11));
        assertEquals (0, game.getWinner ( ).getBoard().getPointTokens ());
        p1.getBoard().addPointTokens (8);
        p2.getBoard().addPointTokens (2);
        p3.getBoard().addPointTokens (16);
        p4.getBoard().addPointTokens (4);
        p5.getBoard().addPointTokens (6);
        game.updateRanking();
        assertEquals (16, game.getWinner ( ).getBoard().getPointTokens ());
        assertEquals (8, game.getRanking ().get(1).getBoard().getPointTokens ());
        assertEquals (6, game.getRanking ().get(2).getBoard().getPointTokens ());
        assertEquals (4, game.getRanking ().get(3).getBoard().getPointTokens ());
        assertEquals (2, game.getRanking ().get(4).getBoard().getPointTokens ());
    }

    @Test
    void testTiedPlayers(){

        p1.getBoard().addPointTokens(8);
        p2.getBoard().addPointTokens(7);
        p3.getBoard().addPointTokens(8);
        p4.getBoard().addPointTokens(5);
        p5.getBoard().addPointTokens(2);

        deathTrack.add(p1.getPlayerColor());
        deathTrack.add(p1.getPlayerColor());
        deathTrack.add(p3.getPlayerColor());
        deathTrack.add(p1.getPlayerColor());
        System.out.println("p1 is: "+p1.getPlayerColor());
        System.out.println("p2 is: "+p2.getPlayerColor());
        System.out.println("p3 is: "+p3.getPlayerColor());
        System.out.println("p4 is: "+p4.getPlayerColor());
        System.out.println("p5 is: "+p5.getPlayerColor());

        game.updateRanking();
        for (Player p: game.getRanking()) {
            System.out.println(p.getPlayerColor());
        }

    }
}
