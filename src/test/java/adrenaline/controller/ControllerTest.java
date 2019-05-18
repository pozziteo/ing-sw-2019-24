package adrenaline.controller;

import adrenaline.model.Game;
import adrenaline.model.GameModel;
import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.deck.powerup.PowerUpType;
import adrenaline.model.player.Player;
import adrenaline.network.Lobby;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";
    private String[] playerNames = {"luca", "matteo", "sara", "pippo", "baudo"};
    private Game game = new Game (playerNames);
    private Player p1 = game.getPlayers ().get(0);
    private Player p2 = game.getPlayers ().get(1);
    private Player p3 = game.getPlayers ().get(2);
    private Player p4 = game.getPlayers ().get(3);
    private Player p5 = game.getPlayers ().get(4);

    private Ammo ammo1 = Ammo.BLUE_AMMO;
    private PowerUpType powerUpType = PowerUpType.TARGETING_SCOPE;
    private PowerUp powerUp = new PowerUp(powerUpType, ammo1);
    private ArrayList<PowerUp> pupList = new ArrayList<>(Arrays.asList(powerUp));



    private Lobby lobby;
    private GameModel gameModel;
    private Controller controller = new Controller(lobby);

    @Test
    void spawnPoint(){
        this.game.setArena(SMALL);
        p1.setPosition(game.getMap().getSquare(2));
        p2.setPosition(game.getMap().getSquare(4));
        p3.setPosition(game.getMap().getSquare(2));
        p4.setPosition(game.getMap().getSquare(11));
        p5.setPosition(game.getMap().getSquare(11));
        System.out.println(p5.getPosition().isSpawnPoint());
    }
}