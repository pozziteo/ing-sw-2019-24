package model.deck;

import model.player.*;

public class Attack {
    private int damage;
    private int marks;
    private Player attacker;
    private Player victim;

    public Attack(int d, int m, Player a, Player b){
        this.damage = d;
        this.marks = m;
        this.attacker = a;
        this.victim = b;
    }

    /**
     * Getter for the weapon's damage
     * @return damage
     */
    public int getDamageWeapon(){return this.damage;}


    /**
     * Getter for the marks given by the weapon
     * @return marks
     */
    public int getMarksWeapon() {return this.marks;}


    /**
     * Getter for the player's color
     * @return attacker
     */
    public Player getAttacker() {return this.attacker;}


    /**
     * Getter for the victim' color
     * @return victim
     */
    public Player getVictim() {return this.victim;}



    /**
     * This method adds damages to the victim
     * @param attacker is the ID of the player who gives the damage
     * @param victim is the ID of the player who receives the damage
     * @param damage is the number of damage
     */
    public void giveDamage(Player attacker, Player victim, int damage) {
        for (int i = 0; i < damage; i++) {
            victim.getBoard().getDamage().add(attacker.getPlayerColor());

        }

    }

    public void giveMarks() {
        attacker.giveMark(marks, victim);
    }


}
