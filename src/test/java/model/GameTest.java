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
        game.getPlayers ().get(0).setPosition(game.getArena().getSquare(2));
        game.getPlayers ().get(1).setPosition(game.getArena().getSquare(4));
        game.getPlayers ().get(2).setPosition(game.getArena().getSquare(11));
        game.getPlayers ().get(0).setPointTokens (1);
        game.getPlayers ().get(1).setPointTokens (6);
        game.getPlayers ().get(2).setPointTokens (2);
        game.updateRanking ();
        assertEquals ("yellow", game.getWinner ( ).getPlayerColor ( ));
        game.getPlayers ().get(0).addPointTokens (10);
        game.getPlayers ().get(1).addPointTokens (8);
        game.getPlayers ().get(2).addPointTokens (16);
        game.updateRanking();
        assertEquals ("blue", game.getWinner ( ).getPlayerColor ( ));
    }
}
