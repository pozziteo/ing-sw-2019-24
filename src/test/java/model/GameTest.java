package model;

import model.player.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testWinner() throws FileNotFoundException {
        Game game = Game.getGameInstance ();
        ArrayList<Player> players = new ArrayList<>();
        game.setArena ("maps\\smallmap.json");
        Player p1 = new Player("red", game.getArena ().getSquare (2));
        p1.setPointTokens (16);
        players.add (p1);
        Player p2 = new Player("yellow", game.getArena ().getSquare (4));
        p2.setPointTokens (8);
        players.add (p2);
        Player p3 = new Player("blue", game.getArena ().getSquare (11));
        p3.setPointTokens (2);
        players.add (p3);
        game.setPlayers (players);
        game.setSkullsRemaining (0);
        game.getSkullsRemaining ();
        assertEquals("red", game.getRanking ().get(0).getPlayerColor ());
    }
}
