package adrenaline.model;

import adrenaline.model.player.Player;
import org.junit.jupiter.api.Test;

import java.io.File;
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

    @Test
    void testPoints(){
        System.out.println("p4 is " +p4.getPlayerColor());
        System.out.println("p2 is " +p2.getPlayerColor());
        System.out.println("p3 is " +p3.getPlayerColor());
        System.out.println("p5 is " +p5.getPlayerColor());
        p1.getBoard().gotHit(2, p2); //p2 ha 2 colpi
        p1.getBoard().gotHit(5, p3); //p3 ha 4 colpi
        p1.getBoard().gotHit(2, p4); //p4 ha 2 colpi
        p1.getBoard().gotHit(2, p5); //p5 ha 3 colpi
//        p1.getBoard().gotHit(1, p2);
//        p1.getBoard().gotHit(3, p3);
//        p1.getBoard().gotHit(1, p4);
//        p1.getBoard().gotHit(1, p3);
        System.out.println("Array di p1: "+p1.getBoard().getDamageTaken());
        game.givePoints(p1);
        System.out.println("p2: "+p2.getPointTokens());
        System.out.println("p3: "+p3.getPointTokens());
        System.out.println("p4: "+p4.getPointTokens());
        System.out.println("p5: "+p5.getPointTokens());
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
        assertEquals (0, game.getWinner ( ).getPointTokens ());
        p1.addPointTokens (8);
        p2.addPointTokens (2);
        p3.addPointTokens (16);
        p4.addPointTokens (4);
        p5.addPointTokens (6);
        game.updateRanking();
        assertEquals (16, game.getWinner ( ).getPointTokens ());
        assertEquals (8, game.getRanking ().get(1).getPointTokens ());
        assertEquals (6, game.getRanking ().get(2).getPointTokens ());
        assertEquals (4, game.getRanking ().get(3).getPointTokens ());
        assertEquals (2, game.getRanking ().get(4).getPointTokens ());
    }
}
