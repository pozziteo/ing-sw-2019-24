package adrenaline.model.deck;

import adrenaline.exceptions.IllegalUseOfPowerUpException;
import adrenaline.exceptions.NotEnoughAmmoException;
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

    private static final String PATH ="/maps/";
    private static final String SMALL = PATH +"smallmap.json";
    private String[] playerNames = {"luca", "sara", "matteo"};
    private Game game = new Game(playerNames);
    private Player attacker = game.getPlayers ().get(0);
    private Player victim = game.getPlayers ().get(1);
    private Ammo redAmmo = Ammo.RED_AMMO;
    private Ammo blueAmmo = Ammo.BLUE_AMMO;
    private Ammo yellowAmmo = Ammo.YELLOW_AMMO;

    private PowerUpType powerUpType = PowerUpType.TARGETING_SCOPE;
    private PowerUp targetingScope = new PowerUp(powerUpType, redAmmo);
    private PowerUpType powerUpType1 = PowerUpType.NEWTON;
    private PowerUp newton = new PowerUp(powerUpType1, yellowAmmo);
    private PowerUpType powerUpType2 = PowerUpType.TELEPORTER;
    private PowerUp teleporter = new PowerUp(powerUpType2, blueAmmo);
    private PowerUpType powerUpType3 = PowerUpType.TAGBACK_GRENADE;
    private PowerUp tagbackGrenade = new PowerUp(powerUpType3, redAmmo);
    private ArrayList<PowerUp> pupList = new ArrayList<>(Arrays.asList(targetingScope, newton, teleporter, tagbackGrenade));

    @Test
    void powerUpAsAmmoTest(){
        attacker.setOwnedPowerUps(pupList);
        PowerUpEffect effect = new PowerUpEffect (attacker, pupList.get (0));
        effect.usePupAmmo();
        assertEquals(3, attacker.getOwnedPowerUps().size());
        assertEquals(2, attacker.getBoard().getAmountOfAmmo(redAmmo));
    }

    @Test
    void testTargetingScope() {
        game.setArena (SMALL);
        attacker.setPosition (game.getMap ().getSquare (0));
        victim.setPosition (game.getMap ().getSquare (0));
        attacker.setOwnedPowerUps(pupList);
        PowerUpEffect effect = new PowerUpEffect (attacker, pupList.get (0));
        try {
            effect.useTargetingScope (victim);
        } catch (NotEnoughAmmoException e) {
            //should not be thrown
        }
        assertEquals(3, attacker.getOwnedPowerUps().size());
        assertEquals (1, victim.getBoard ().getDamageAmountGivenByPlayer (attacker));
    }

    @Test
    void testNewton() {
        game.setArena (SMALL);
        attacker.setPosition (game.getMap ().getSquare (0));
        victim.setPosition (game.getMap ().getSquare (0));
        attacker.setOwnedPowerUps(pupList);
        PowerUpEffect effect = new PowerUpEffect (attacker, pupList.get (1));
        try {
            effect.useNewton (victim, 1);
        } catch (IllegalUseOfPowerUpException e) {
            //should not be thrown
        }
        assertEquals(3, attacker.getOwnedPowerUps().size());
        assertEquals (1, victim.getPosition ().getSquareId ());
    }

    @Test
    void testTeleporter() {
        game.setArena (SMALL);
        attacker.setPosition (game.getMap ().getSquare (0));
        victim.setPosition (game.getMap ().getSquare (0));
        attacker.setOwnedPowerUps(pupList);
        PowerUpEffect effect = new PowerUpEffect (attacker, pupList.get (2));
        effect.useTeleporter (1);
        assertEquals(3, attacker.getOwnedPowerUps().size());
        assertEquals (1, attacker.getPosition ().getSquareId ());
    }

    @Test
    void testTagbackGrenade() {
        attacker.setOwnedPowerUps(pupList);
        PowerUpEffect effect = new PowerUpEffect (attacker, pupList.get (3));
        effect.useTagbackGrenade (victim);
        assertEquals(3, attacker.getOwnedPowerUps().size());
        assertEquals (1, victim.getBoard ().getMarksAmountGivenByPlayer (attacker));
    }
}