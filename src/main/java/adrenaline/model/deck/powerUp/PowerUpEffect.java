package adrenaline.model.deck.powerUp;

import adrenaline.exceptions.IllegalUseOfPowerUpException;
import adrenaline.exceptions.InvalidPositionException;
import adrenaline.exceptions.ReachedMaxAmmoLimitException;
import adrenaline.model.Game;
import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.AtomicEffectsFactory;
import adrenaline.model.deck.TargetType;
import adrenaline.model.map.Square;
import adrenaline.model.player.Player;

import java.util.List;

public class PowerUpEffect {
    private PowerUpRequirement requirements;
    private PowerUp pup;
    private TargetType target;
    private Player attacker;
    private Player victim;
    private Square positionToGo;
    private AtomicEffectsFactory factory = new AtomicEffectsFactory();
    private Game game;
    private Ammo ammo;

    public PowerUpEffect(){

    }

    /**
     * Method for the Teleport Power Up.
     * If the player chooses to teleport himself into his starting square the method recalls itself
     * @param attacker is the player who uses the card
     * @param positionToGo is the position where you want to teleport
     * @throws InvalidPositionException
     */
    public void teleport(Player attacker, Square positionToGo) throws InvalidPositionException {
        if (attacker.getPosition() == positionToGo){
            throw new InvalidPositionException("The position you want to teleport to is your starting position");
        }
        attacker.setPosition(positionToGo);
    }

    /**
     * Method for Newton Power Up
     * @param attacker is the attacker
     * @param movements is the number of squares the victim moves through
     */
    public void newton(String attacker, int movements){
        factory.createGenericMovementEffect(attacker, movements);
    }

    /**
     * Method for Targeting Scope Power Up,
     * @param attacker is the attacker
     * @param victim is the victim
     * @throws IllegalUseOfPowerUpException
     */
    public void targetingScope(Player attacker, Player victim) throws IllegalUseOfPowerUpException {
        if (game.getCurrentTurn() != attacker.getGame().getCurrentTurn()){
            throw new IllegalUseOfPowerUpException("You must inflict damage to the victim in this turn to use the Targeting Scope");
        }
        if (attacker.getBoard().getAmountOfAmmo(ammo) == 0){
            throw new IllegalUseOfPowerUpException("You don't have enough ammo to use the Targeting Scope");
        }
        if (victim.getBoard().getDamageAmountGivenByPlayer(attacker ) == 0) {
            throw new IllegalUseOfPowerUpException("You can't use the Targeting Scope without inflicting damage to the victim");
        }
        victim.getBoard().gotHit(1, attacker);
    }

    /**
     * Method for Tagback Grenade Power Up
     * @param attacker
     * @param victim
     * @throws IllegalUseOfPowerUpException
     */
    public void tagbackGreade(Player attacker, Player victim) throws IllegalUseOfPowerUpException{
        if (!attacker.canSee(victim)){
            throw new IllegalUseOfPowerUpException("You can't see the victim");
        }
        if (attacker.getBoard().getDamageAmountGivenByPlayer(victim)==0){
            throw new IllegalUseOfPowerUpException("You must receive damage from the target");
        }
        if (attacker.getGame().getCurrentTurn() != victim.getGame().getCurrentTurn()){
            throw new IllegalUseOfPowerUpException("You must receive the damage in the same turn you use the Tagback Grenade");
        }
        victim.getBoard().gotMarked(1, attacker);
    }

    /**
     * Method for using the ammo in the PowerUp card
     * @param attacker is the attacker
     * @param pup is the PowerUp
     * @param cost is the reduction cost
     */
    public void usePupAmmo(Player attacker, PowerUp pup, List<Ammo> cost){
        //TODO fix method with reduction cost
        attacker.getBoard().setOwnedAmmo(pup.getAmmo());
    }


}
