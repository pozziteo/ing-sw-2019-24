package adrenaline.model.player;

import adrenaline.model.Game;
import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.deck.powerup.PowerUpEffect;
import adrenaline.model.map.Square;

import java.util.List;

//TODO add JavaDoc
public class PowerUpAction {

    private Game game;
    private Ammo ammo;
    private List<PowerUp> powerUps;
    //private PowerUpEffect powerUpEffect = new PowerUpEffect(game, ammo);
    private Square square;
    private Player attacker;
    private Player target;
    private int movements;
    private int id;

    /**
     * Constructor of the action PowerUpAction
     * @param nickname is the player who performs the action
     */
    public PowerUpAction(String nickname){
        for (int i=0; i<this.game.getPlayers().size(); i++){
            if (game.getPlayers().get(i).getPlayerName().equalsIgnoreCase(nickname)) {
                powerUps = game.getPlayers().get(i).getOwnedPowerUps();
                break;
            }
        }

    }

    /**
     * Method that lets the player use the PowerUp
     * @param powerUp is the powerUp
     */
    public void usePowerUp(PowerUp powerUp) {
        switch (powerUp.getPowerUpsName()){
            case "Teleporter":
                //powerUpEffect.useTeleporter(attacker, square);
                break;
            case "Targeting Scope":
               // powerUpEffect.useTargetingScope(attacker, target);
                break;
            case "Tagback Grenade":
               // powerUpEffect.useTagbackGrenade(attacker, target);
                break;
            case "Newton":
               // powerUpEffect.useNewton(attacker, target, movements, id);
                break;
        }
    }

    /**
     * Method to use the powerup's ammo
     * @param powerUp is the powerUp
     * @return a boolean.
     */
    public boolean useAmmo(PowerUp powerUp){
      //  powerUpEffect.usePupAmmo(attacker, powerUp);
        return true;
    }
}
