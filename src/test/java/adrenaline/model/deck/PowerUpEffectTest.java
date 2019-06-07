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
import java.util.ArrayList;
import java.util.Arrays;
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
    private PowerUpType powerUpType = PowerUpType.TARGETING_SCOPE;
    private PowerUp powerUp = new PowerUp(powerUpType, ammo1);
    private PowerUpType powerUpType1 = PowerUpType.NEWTON;
    private PowerUp powerUp1 = new PowerUp(powerUpType1, ammo);
    private PowerUpType powerUpType2 = PowerUpType.TELEPORTER;
    private PowerUp powerUp2 = new PowerUp(powerUpType2, ammo);
    private PowerUpType powerUpType3 = PowerUpType.TAGBACK_GRENADE;
    private PowerUp powerUp3 = new PowerUp(powerUpType3, ammo);
    private ArrayList<PowerUp> pupList = new ArrayList<>(Arrays.asList(powerUp, powerUp1, powerUp2, powerUp3));

    @Test
    void ammoPowerUpTest(){
        attacker.getBoard().setOwnedAmmo(ammo1);
        attacker.setOwnedPowerUps(pupList);
        powerUpEffect.usePupAmmo(attacker, powerUp);
        assertEquals(3, attacker.getOwnedPowerUps().size());
        assertEquals(1, attacker.getBoard().getAmountOfAmmo(ammo));
    }

    @Test
    void trueTestPup(){
        this.game.setArena(SMALL);
        attacker.setPosition(game.getMap().getSquare(1));
        victim.setPosition(game.getMap().getSquare(2));
        attacker.setOwnedPowerUps(pupList);

        //targeting scope
        try {
            victim.getBoard().gotHit(3, attacker);
            attacker.getBoard().setOwnedAmmo(ammo);
            powerUpEffect.useTargetingScope(attacker, victim);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        assertEquals(3, attacker.getOwnedPowerUps().size());
        assertEquals(4, victim.getBoard().getDamageAmountGivenByPlayer(attacker));

        //tagback grenade
        try {
            attacker.getBoard().gotHit(2, victim);
            powerUpEffect.useTagbackGrenade(attacker, victim);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        assertEquals(2, attacker.getOwnedPowerUps().size());
        assertEquals(1, victim.getBoard().getMarksAmountGivenByPlayer(attacker));

        //teleporter
        try {
            powerUpEffect.useTeleporter(attacker, game.getMap().getSquare(9));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        assertEquals(1, attacker.getOwnedPowerUps().size());
        assertEquals(9, attacker.getPosition().getSquareId());

        //newton
        try {
            powerUpEffect.useNewton(attacker, victim, 1, 1);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        assertEquals(0, attacker.getOwnedPowerUps().size());
        assertEquals(1, victim.getPosition().getSquareId());
    }

    @Test
    void falseTestPup(){
        this.game.setArena(SMALL);
        attacker.setPosition(game.getMap().getSquare(5));
        victim.setPosition(game.getMap().getSquare(6));

        //targeting scope without dealing damage to the victim
        assertThrows(IllegalUseOfPowerUpException.class, () -> {
            System.out.println(attacker.getBoard().getOwnedAmmo().size()); powerUpEffect.useTargetingScope(attacker, victim);});

        //targeting scope without ammo
        assertThrows(IllegalUseOfPowerUpException.class, () -> {victim.getBoard().gotHit(3, attacker);
                                                                attacker.getBoard().getOwnedAmmo().clear();
                                                                powerUpEffect.useTargetingScope(attacker, victim);});

        //teleport in your position
        assertThrows(InvalidPositionException.class, () -> powerUpEffect.useTeleporter(attacker, game.getMap().getSquare(5)));

        //tagback grenade without damage
        assertThrows(IllegalUseOfPowerUpException.class, () -> powerUpEffect.useTagbackGrenade(attacker, victim));

        //tagback grenade without seeing the victim
        assertThrows(IllegalUseOfPowerUpException.class, () -> {attacker.setPosition(game.getMap().getSquare(1));
                                                                attacker.getBoard().gotHit(5, victim);
                                                                powerUpEffect.useTagbackGrenade(attacker, victim);});

        //newton outside map > 12
        assertThrows(InvalidPositionException.class, () -> powerUpEffect.useNewton(attacker, victim, 1, 12));

        //newton outside map <0
        assertThrows(InvalidPositionException.class, () -> powerUpEffect.useNewton(attacker, victim, 1, -1));
    }
}