package adrenaline.model;

import adrenaline.data.data_for_client.responses_for_view.*;
import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.map.NormalSquare;
import adrenaline.model.map.SpawnPoint;
import adrenaline.model.player.Player;
import adrenaline.network.Account;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//TODO javadoc

public class GameModel {

    private Game game;

    public GameModel(String[] playerNames) {
        this.game = new Game(playerNames);
    }

    public Game getGame() {
        return this.game;
    }

    public List<SquareDetails> createSquareDetails() {
        List<SquareDetails> map = new LinkedList<> ();

        for (int i = 0; i < game.getMap ().getDimension (); i++) {
            SquareDetails square;
            List<String> playersNames = new ArrayList<> ();

            for (Player p : game.getMap ().getSquare (i).getPlayersOnSquare ())
                playersNames.add(p.getPlayerName ());
            if (game.getMap ().getSquare (i).isSpawnPoint ()) {
                String[] weapons = new String[3];
                for (int j = 0; j < 3; j++) {
                    weapons[j] = ((SpawnPoint) game.getMap ().getSquare (i)).getWeapons ()[j].getWeaponsName ();
                }
                square = new SpawnPointDetails (i, playersNames, weapons);

            } else {
                String tile = ((NormalSquare)game.getMap ().getSquare (i)).getPlacedTile ().getTileDescription ();
                square = new NormalSquareDetails (i, playersNames, tile);
            }
            map.add(square);
        }
        return map;
    }

    public List<PowerUpDetails> createPowerUpDetails(Player p) {
        List<PowerUpDetails> powerUps = new ArrayList<> ();
        for (PowerUp pup : p.getOwnedPowerUps ()) {
            PowerUpDetails powerUpDetails = new PowerUpDetails (pup.getPowerUpsName (), pup.getAmmo ().getColor ());
            powerUps.add(powerUpDetails);
        }
        return powerUps;
    }

    public WeaponDetails createWeaponDetail(Weapon w) {
        List<String> ammoList = new ArrayList<> ();
        List<String> grabList = new ArrayList<> ();

        for (Ammo a : w.getType ().getReloadingAmmo ())
            ammoList.add(a.getColor ());

        for (Ammo a : w.getType ().getGrabbingCost ())
            grabList.add(a.getColor ());

        return new WeaponDetails (w.getWeaponsName (), w.getWeaponsDescription (), ammoList, grabList);
    }

    public void updateMap(Account account) {
        MapResponse response = new MapResponse (game.getMap ().getMapName ());
        response.setAccount (account);
        response.sendToView ();
    }

    public void updateSquareDetails(Account account) {
        SquareDetailsResponse response = new SquareDetailsResponse (createSquareDetails ());
        response.setAccount (account);
        response.sendToView ();
    }

    public void updateRanking(Account account) {
        List<String> ranking = new ArrayList<> ();
        for (Player p : game.getRanking ())
            ranking.add(p.getPlayerName ());
        RankingResponse response = new RankingResponse (ranking);
        response.setAccount (account);
        response.sendToView ();
    }

    public void updateBoards(Account account) {
        //TODO
    }

    public void updatePlayerBoard(Account account) {
        //TODO
    }

}
