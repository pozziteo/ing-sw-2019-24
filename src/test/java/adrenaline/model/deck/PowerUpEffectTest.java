package adrenaline.model.deck;

import adrenaline.exceptions.IllegalUseOfPowerUpException;
import adrenaline.exceptions.InvalidPositionException;
import adrenaline.model.Game;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.deck.powerup.PowerUpEffect;
import adrenaline.model.deck.powerup.PowerUpType;
import adrenaline.model.player.Player;
import org.junit.jupiter.api.Test;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PowerUpEffectTest {

    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";
    private String[] playerNames = {"luca", "sara"};
    private Game game = new Game(playerNames);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);
    private Ammo ammo = Ammo.RED_AMMO;
    private Ammo ammo1 = Ammo.BLUE_AMMO;
    private Player attacker = p1;
    private Player victim = p2;
    private PowerUpEffect powerUpEffect = new PowerUpEffect(game, ammo);
    private PowerUpType powerUpType = PowerUpType.TAGBACK_GRENADE;
    private PowerUp powerUp = new PowerUp(powerUpType, ammo1);

    @Test
    void ammoPowerUpTest(){
        //ammo taken from PowerUp
        attacker.getBoard().setOwnedAmmo(ammo1);
        powerUpEffect.usePupAmmo(attacker, powerUp);
        assertEquals(1, attacker.getBoard().getAmountOfAmmo(ammo));
    }

    @Test
    void trueTestPup(){
        this.game.setArena(SMALL);
        attacker.setPosition(game.getMap().getSquare(1));
        victim.setPosition(game.getMap().getSquare(2));

        //targeting scope
        try {
            attacker.getBoard().setOwnedAmmo(ammo);
            victim.getBoard().gotHit(3, attacker);
            powerUpEffect.targetingScope(attacker, victim);
        } catch (IllegalUseOfPowerUpException e){
            System.err.println(e.getMessage());
        }
        assertEquals(4, victim.getBoard().getDamageAmountGivenByPlayer(attacker));

        //tagback grenade
        try {
            attacker.getBoard().gotHit(2, victim);
            powerUpEffect.tagbackGreade(attacker, victim);
        }catch (IllegalUseOfPowerUpException e){
            System.err.println(e.getMessage());
        }
        assertEquals(1, victim.getBoard().getMarksAmountGivenByPlayer(attacker));

        //teleport
        try {
            powerUpEffect.teleport(attacker, game.getMap().getSquare(9));
        }catch (InvalidPositionException e){
            System.err.println(e.getMessage());
        }
        assertEquals(9, attacker.getPosition().getSquareId());

        //newton
        try {
            powerUpEffect.newton(attacker, victim, 1, 1);
        }catch (InvalidPositionException e){
            System.err.println(e.getMessage());
        }
        assertEquals(1, victim.getPosition().getSquareId());
    }

    @Test
    void falseTestPup(){
        this.game.setArena(SMALL);
        attacker.setPosition(game.getMap().getSquare(5));
        victim.setPosition(game.getMap().getSquare(6));

        //targeting scope without ammo
        assertThrows(IllegalUseOfPowerUpException.class, () -> {victim.getBoard().gotHit(0, attacker);
            powerUpEffect.targetingScope(attacker, victim);});

        //targeting scope without dealing damage to the victim
        assertThrows(IllegalUseOfPowerUpException.class, () -> {attacker.getBoard().setOwnedAmmo(ammo);
                                                                powerUpEffect.targetingScope(attacker, victim);});

        //teleport in your position
        assertThrows(InvalidPositionException.class, () -> powerUpEffect.teleport(attacker, game.getMap().getSquare(5)));

        //tagback grenade without damage
        assertThrows(IllegalUseOfPowerUpException.class, () -> powerUpEffect.tagbackGreade(attacker, victim));

        //tagback grenade without seeing the victim
        assertThrows(IllegalUseOfPowerUpException.class, () -> {attacker.setPosition(game.getMap().getSquare(1));
                                                                attacker.getBoard().gotHit(5, victim);
                                                                powerUpEffect.tagbackGreade(attacker, victim);});

        //newton outside map > 12
        assertThrows(InvalidPositionException.class, () -> powerUpEffect.newton(attacker, victim, 1, 12));

        //newton outside map <0
        assertThrows(InvalidPositionException.class, () -> powerUpEffect.newton(attacker, victim, 1, -1));
    }
}