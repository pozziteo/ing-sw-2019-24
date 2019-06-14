package adrenaline.model.deck.powerup;

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
     * @param id is the id of the square the player wants to teleport to
     */
    public void useTeleporter(int id) {
        Square square = null;
        for (Square s : user.getGame ().getMap ().getArena ())
            if (s.getSquareId () == id)
                square = s;
        user.setPosition(square);
        removePowerUp(user, toUse);
        user.getGame().getPowerUpsDeck().discardCard(toUse);
    }

    /**
     * Method for Newton Power Up
     * @param victim is the player you want to move
     * @param id is the new victim's square
     */
    public void useNewton(Player victim, int id) {
        AtomicWeaponEffect effect = factory.createGenericMovementEffect("target", 2);
        effect.applyEffect(user, victim, id);
        removePowerUp(user, toUse);
    }

    /**
     * Method for Targeting Scope Power Up
     * @param victim is the victim
     */
    public void useTargetingScope(Player victim) throws NotEnoughAmmoException {
        if (user.getBoard().getOwnedAmmo().isEmpty()){
            throw new NotEnoughAmmoException("You don't have enough ammo to use this power up.");
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
    public void useTagbackGrenade(Player victim) {
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
