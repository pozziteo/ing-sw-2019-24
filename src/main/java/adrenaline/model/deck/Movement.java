package adrenaline.model.deck;

import adrenaline.model.map.Square;
import adrenaline.model.player.Player;

public class Movement {

    private int move;
    private Player attacker;
    private Player victim;
    private Square toSquare;
    private static final int STILL=0;
    private static final int SHORT=1;
    private static final int LONG=2;


    /**
     * This method changes the position of the victim. the attacker and the victim can't stay
     * in the same square before the method is called
     * @param attacker is the player who attracts the victim
     * @param victim is the player who receive the movement
     * @param move is the number of square the victim passes through (0 to 2)
     */
    public void moveToMe(Player attacker, Player victim, int move){

//        TODO need to adapt to new features
        /*if(attacker.getPosition().getY()==victim.getPosition().getY()) {
            if (attacker.getPosition().getX() > victim.getPosition().getX()) {
                int x = victim.getPosition().getX();
                victim.getPosition().setX(x + move);

            }
            if (attacker.getPosition().getX() < victim.getPosition().getX()) {
                int x = victim.getPosition().getX();
                victim.getPosition().setX(x - move);

            }
        }

        if(attacker.getPosition().getX()==victim.getPosition().getX()) {
            if (attacker.getPosition().getY() > victim.getPosition().getY()) {
                int y = victim.getPosition().getY();
                victim.getPosition().setY(y + move);

            }
            if (attacker.getPosition().getY() < victim.getPosition().getY()) {
                int y = victim.getPosition().getY();
                victim.getPosition().setY(y - move);

            }
        } */
    }


    /**
     * This method changes the position of the victim.
     * @param attacker is the player who pushes away the victim
     * @param victim is the player who receives the movement
     * @param move is the number of square the victim passes through (0 to 2)
     */
    public void moveFromMe(Player attacker, Player victim, int move){

//        TODO need to adapt to new features
        /*if(attacker.getPosition().getY()==victim.getPosition().getY()) {
            if (attacker.getPosition().getX() > victim.getPosition().getX()) {
                int x = victim.getPosition().getX();
                victim.getPosition().setX(x - move);

            }
            if (attacker.getPosition().getX() < victim.getPosition().getX()) {
                int x = victim.getPosition().getX();
                victim.getPosition().setX(x + move);

            }
        }

        if(attacker.getPosition().getX()==victim.getPosition().getX()) {
            if (attacker.getPosition().getY() > victim.getPosition().getY()) {
                int y = victim.getPosition().getY();
                victim.getPosition().setY(y - move);

            }
            if (attacker.getPosition().getY() < victim.getPosition().getY()) {
                int y = victim.getPosition().getY();
                victim.getPosition().setY(y + move);

            }
        }

        if ((attacker.getPosition().getY()==victim.getPosition().getY()) &&(attacker.getPosition().getX()==victim.getPosition().getX())){

            //the attacker chooses the direction where to move away the victim (maybe controller?)

            int x = victim.getPosition().getX();
            victim.getPosition().setX(x - move);
            victim.getPosition().setX(x + move);

            int y = victim.getPosition().getY();
            victim.getPosition().setY(y - move);
            victim.getPosition().setY(y + move);

        }
        */
    }

    public void moveToSquare (Player victim, int move, Square toSquare){
//        TODO need to adapt to new features
        //the attacker chooses the square where to move the victim (maybe controller?)
        //only few weapon can do it
    }


}
