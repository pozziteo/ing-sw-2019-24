package model.deck;

import model.player.Player;
import model.player.Board;

public class Attack {

    private int damage;
    private int marks;
    private Player attacker;
    private Player victim;


    /**
     * @param attacker is the ID of the player who gives the damage
     * @param victim is the ID of the player who receives the damage
     * @param damage is the number of damage
     */
    public void giveDamage(Player attacker, Player victim, int damage) {
        for (int i = 0; i < damage; i++) {
            victim.getBoard().getDamage().add(attacker.getPlayerColor());

        }

    }



}
