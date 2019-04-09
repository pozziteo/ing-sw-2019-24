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
    public int getDamage(){return this.damage;}


    /**
     * Getter for the marks given by the weapon
     * @return marks
     */
    public int getMarks() {return this.marks;}


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
     * Method to add damage to the victim
     */
    public void giveDamage() {
        this.getVictim ().getBoard().gotHit (this.getDamage (), this.getAttacker ());
    }

    /**
     * Method to add marks to the victim
     */

    public void giveMarks() {
        this.getAttacker().giveMark(this.getMarks (), this.getVictim ());
    }
}
