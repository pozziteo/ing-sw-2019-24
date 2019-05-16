package adrenaline.model.deck.powerup;

import adrenaline.exceptions.IllegalUseOfPowerUpException;
import adrenaline.exceptions.InvalidPositionException;
import adrenaline.model.Game;
import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.AtomicEffectsFactory;
import adrenaline.model.deck.AtomicWeaponEffect;
import adrenaline.model.map.Square;
import adrenaline.model.player.Player;

public class PowerUpEffect {
    private AtomicEffectsFactory factory = new AtomicEffectsFactory();
    private Game game;
    private Ammo ammo;

    public PowerUpEffect(Game game, Ammo ammo){
        this.game = game;
        this.ammo = ammo;
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
     * @param victim is the player you want to move
     * @param movements is the number of movements (1 or 2)
     * @param id is the new victim's square
     * @throws InvalidPositionException
     */
    public void newton(Player attacker, Player victim, int movements, int id) throws InvalidPositionException{
        if (0 > id || id > 11){
            throw new InvalidPositionException("The position you chose is outside the map");
        }
        if(game.getMap().getSquare(id).getSquareColor().equals("none")){
            throw new InvalidPositionException("The position you chose doesn't exist in this map");
        }
        AtomicWeaponEffect effect = factory.createGenericMovementEffect("target", movements);
        effect.applyEffect(attacker, victim, id);
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
     * @param attacker is the attacker
     * @param victim is the victim
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
     * Method to use the ammo of the Power Up
     * @param attacker is the player who uses the ammo
     * @param pup is the Power Up
     * @return a boolean
     */
    public boolean usePupAmmo(Player attacker, PowerUp pup){
        attacker.getBoard().setOwnedAmmo(pup.getAmmo());
        return true;
    }
}
