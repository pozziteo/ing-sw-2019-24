package adrenaline.model.deck.powerup;

import adrenaline.exceptions.IllegalUseOfPowerUpException;
import adrenaline.exceptions.InvalidPositionException;
import adrenaline.exceptions.NotEnoughAmmoException;
import adrenaline.model.deck.AtomicEffectsFactory;
import adrenaline.model.deck.AtomicWeaponEffect;
import adrenaline.model.map.Square;
import adrenaline.model.player.Player;

public class PowerUpEffect {
    private AtomicEffectsFactory factory = new AtomicEffectsFactory();
    private Player user;
    private PowerUp toUse;

    public PowerUpEffect(Player user, PowerUp toUse){
        this.user = user;
        this.toUse = toUse;
    }

    /**
     * Method for the Teleport Power Up.
     * If the player chooses to teleport himself into his starting square the method recalls itself
     * @param positionToGo is the position where you want to teleport
     */
    public void useTeleporter(Square positionToGo) throws InvalidPositionException {
        if (user.getPosition() == positionToGo){
            throw new InvalidPositionException("The position you want to teleport to is your starting position");
        } else {
            user.setPosition(positionToGo);
            removePowerUp(user, toUse);
            user.getGame().getPowerUpsDeck().discardCard(toUse);
        }
    }

    /**
     * Method for Newton Power Up
     * @param victim is the player you want to move
     * @param movements is the number of movements (1 or 2)
     * @param id is the new victim's square
     */
    public void useNewton(Player victim, int movements, int id) throws InvalidPositionException {
        if (0 > id || id > 11){
            throw new InvalidPositionException("The position you chose is outside the map");
        }
        AtomicWeaponEffect effect = factory.createGenericMovementEffect("target", movements);
        effect.applyEffect(user, victim, id);
        removePowerUp(user, toUse);
    }

    /**
     * Method for Targeting Scope Power Up
     * @param victim is the victim
     */
    public void useTargetingScope(Player victim) throws NotEnoughAmmoException {
        if (user.getBoard().getOwnedAmmo().isEmpty()){
            throw new NotEnoughAmmoException();
        } else {
            user.getBoard().getOwnedAmmo().remove(0);
            victim.getBoard().gotHit(1, user);
            removePowerUp(user, toUse);
        }
    }

    /**
     * Method for Tagback Grenade Power Up
     * @param victim is the victim
     */
    public void useTagbackGrenade(Player victim) throws IllegalUseOfPowerUpException {
        if(!user.canSee(victim)){
            throw new IllegalUseOfPowerUpException("You can't see the victim");
        }
        if (user.getBoard().getDamageAmountGivenByPlayer(victim)==0){
            throw new IllegalUseOfPowerUpException("You must receive damage from the target");
        }
        user.giveMark(1, victim);
        removePowerUp(user, toUse);
        user.getGame().getPowerUpsDeck().discardCard(toUse);
    }

    /**
     * Method to use the ammo of the Power Up
     * @return a boolean
     */
    public void usePupAmmo(){
        user.getBoard().setOwnedAmmo(toUse.getAmmo());
        removePowerUp(user, toUse);
    }

    /**
     * Method to remove the PowerUp from player's list of OwnedPowerUp, once used
     * @param player is the player who uses the PowerUp
     * @param powerUp is the PowerUp to remove
     */
    private synchronized void removePowerUp(Player player, PowerUp powerUp) {
        player.getOwnedPowerUps().removeIf(p -> p.getPowerUpsName().equalsIgnoreCase(powerUp.getPowerUpsName()));

    }
}
