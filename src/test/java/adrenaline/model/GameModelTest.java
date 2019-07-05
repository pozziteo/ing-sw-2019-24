package adrenaline.model;

import adrenaline.model.deck.Weapon;
import adrenaline.model.player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

class GameModelTest {
    private static final String PATH = "/maps/";
    private static final String SMALL = PATH +"smallmap.json";
    private String[] playerNames = {"luca", "matteo", "sara", "foo", "bar"};
    private GameModel model = new GameModel (playerNames);

    @Test
    void testGameDetails() {
        model.getGame ().setArena (SMALL);
        model.getGame ().startGame ();
        Player firstPlayer = model.getGame ().getFirstPlayer ();
        firstPlayer.setPosition (model.getGame ().getMap ().getSquare (5));
        assertEquals (10, model.createSquareDetails ().size ());
        assertEquals (2, model.createPowerUpDetails (firstPlayer).size ());
        assertNotNull(model.createWeaponDetail ((Weapon) model.getGame ().getWeaponsDeck ().drawCard ()));
        assertNotNull(model.createWeaponEffects ((Weapon) model.getGame ().getWeaponsDeck ().drawCard ()));
        assertNotNull(model.createTargetDetails (((Weapon) model.getGame ().getWeaponsDeck ().drawCard ()).getBaseEffect ()));
        assertNotNull(model.createBoardDetails(firstPlayer, false));
        assertTrue(model.findPlayersEnabledToTagback ().isEmpty ());
    }
}
