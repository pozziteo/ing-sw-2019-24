package model.deck;

import model.map.Square;
import model.player.Player;

public class Movement {

    private int move;
    private Player attacker;
    private Player victim;
    private Square toSquare;
    private static final int STILL=0;
    private static final int SHORT=1;
    private static final int LONG=2;

    public Square moveToMe (Player attacker, Player victim, int move){

        return victim.getPosition();
    }

    public Square moveFromMe (Player attacker, Player victim, int move){

        return victim.getPosition();
    }

    public Square moveToSquare (Player victim, int move, Square toSquare){

        return victim.getPosition();
    }


}
