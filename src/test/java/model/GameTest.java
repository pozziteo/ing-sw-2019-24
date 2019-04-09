package model;

import model.player.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testWinner() throws FileNotFoundException {
        Game game = new Game (3);
        ArrayList<Player> players = new ArrayList<> ( );
        game.setArena ("maps\\smallmap.json");
        Player p1 = new Player (game, "red", game.getArena ( ).getSquare (2));
        players.add (p1);
        Player p2 = new Player (game, "yellow", game.getArena ( ).getSquare (4));
        players.add (p2);
        Player p3 = new Player (game, "blue", game.getArena ( ).getSquare (11));
        players.add (p3);
        game.setRanking (players);
        p1.setPointTokens (1);
        p2.setPointTokens (6);
        p3.setPointTokens (2);
        game.updateRanking (new ArrayList<> ());
        assertEquals ("yellow", game.getWinner ( ).getPlayerColor ( ));
        p1.addPointTokens (10);
        p2.addPointTokens (8);
        p3.addPointTokens (16);
        game.updateRanking (new ArrayList<> ());
        assertEquals ("blue", game.getWinner ( ).getPlayerColor ( ));
    }
}
