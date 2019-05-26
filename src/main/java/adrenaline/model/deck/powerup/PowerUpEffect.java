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
    private PowerUpType powerUpType0 = PowerUpType.TAGBACK_GRENADE;
    private PowerUpType powerUpType1 = PowerUpType.NEWTON;
    private PowerUpType powerUpType2 = PowerUpType.TARGETING_SCOPE;
    private PowerUpType powerUpType3 = PowerUpType.TELEPORTER;
    private PowerUp pup0 = new PowerUp(powerUpType0, ammo);
    private PowerUp pup1 = new PowerUp(powerUpType1, ammo);
    private PowerUp pup2 = new PowerUp(powerUpType2, ammo);
    private PowerUp pup3 = new PowerUp(powerUpType3, ammo);

    public PowerUpEffect(Game game, Ammo ammo){
        this.game = game;
        this.ammo = ammo;
    }

    /**
     * Method for the Teleport Power Up.
     * If the player chooses to teleport himself into his starting square the method recalls itself
     * @param attacker is the player who uses the card
     * @param positionToGo is the position where you want to teleport
     */
    public void useTeleporter(Player attacker, Square positionToGo) {
        if (attacker.getPosition() != positionToGo){
            attacker.setPosition(positionToGo);
            removePowerUp(attacker, pup3);
            this.game.getPowerUpsDeck().discardCard(pup3);
        }else System.err.println("You cannot use this PowerUp");
    }

    /**
     * Method for Newton Power Up
     * @param attacker is the attacker
     * @param victim is the player you want to move
     * @param movements is the number of movements (1 or 2)
     * @param id is the new victim's square
     */
    public void useNewton(Player attacker, Player victim, int movements, int id) {
        if ((0 <= id) && (id <= 11)){
            AtomicWeaponEffect effect = factory.createGenericMovementEffect("target", movements);
            effect.applyEffect(attacker, victim, id);
            removePowerUp(attacker, pup1);
            this.game.getPowerUpsDeck().discardCard(pup1);
        }else System.err.println("You cannot use this PowerUp");
    }

    /**
     * Method for Targeting Scope Power Up,
     * @param attacker is the attacker
     * @param victim is the victim
     */
    public void useTargetingScope(Player attacker, Player victim) {
        if (!attacker.getBoard().getOwnedAmmo().isEmpty()){
            if (victim.getBoard().getDamageAmountGivenByPlayer(attacker ) > 0) {
                victim.getBoard().gotHit(1, attacker);
                removePowerUp(attacker, pup2);
                this.game.getPowerUpsDeck().discardCard(pup2);
            }else System.err.println("You cannot use this PowerUp");
        }else System.err.println("You cannot use this PowerUp");
    }

    /**
     * Method for Tagback Grenade Power Up
     * @param attacker is the attacker
     * @param victim is the victim
     */
    public void useTagbackGrenade(Player attacker, Player victim) {
        if (attacker.canSee(victim)){
            if (attacker.getBoard().getDamageAmountGivenByPlayer(victim)>0){
                victim.getBoard().gotMarked(1, attacker);
                removePowerUp(attacker, pup0);
                this.game.getPowerUpsDeck().discardCard(pup0);
            }else System.err.println("You cannot use this PowerUp");
        }else System.err.println("You cannot use this PowerUp");


    }

    /**
     * Method to use the ammo of the Power Up
     * @param attacker is the player who uses the ammo
     * @param pup is the Power Up
     * @return a boolean
     */
    public boolean usePupAmmo(Player attacker, PowerUp pup){
        attacker.getBoard().setOwnedAmmo(pup.getAmmo());
        removePowerUp(attacker, pup);
        return true;
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
