package model;

import model.player.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game = new Game (5);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);
    private Player p3 = game.getPlayers ().get(2);
    private Player p4 = game.getPlayers ().get(3);
    private Player p5 = game.getPlayers ().get(4);

    @Test
    public void testWinner() {
        p1.setPosition(game.getArena().getSquare(2));
        p2.setPosition(game.getArena().getSquare(4));
        p3.setPosition(game.getArena().getSquare(11));
        assertEquals ("red", game.getWinner ( ).getPlayerColor ( ));
        p1.addPointTokens (8);
        p2.addPointTokens (2);
        p3.addPointTokens (16);
        p4.addPointTokens (4);
        p5.addPointTokens (6);
        game.updateRanking();
        assertEquals ("blue", game.getWinner ( ).getPlayerColor ( ));
        assertEquals ("red", game.getRanking ().get(1).getPlayerColor ( ));
        assertEquals ("grey", game.getRanking ().get(2).getPlayerColor ( ));
        assertEquals ("green", game.getRanking ().get(3).getPlayerColor ( ));
        assertEquals ("yellow", game.getRanking ().get(4).getPlayerColor ( ));
    }
}
